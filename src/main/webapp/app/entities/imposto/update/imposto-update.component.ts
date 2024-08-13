import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEsfera } from 'app/entities/esfera/esfera.model';
import { EsferaService } from 'app/entities/esfera/service/esfera.service';
import { IImposto } from '../imposto.model';
import { ImpostoService } from '../service/imposto.service';
import { ImpostoFormService, ImpostoFormGroup } from './imposto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-imposto-update',
  templateUrl: './imposto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ImpostoUpdateComponent implements OnInit {
  isSaving = false;
  imposto: IImposto | null = null;

  esferasSharedCollection: IEsfera[] = [];

  protected impostoService = inject(ImpostoService);
  protected impostoFormService = inject(ImpostoFormService);
  protected esferaService = inject(EsferaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ImpostoFormGroup = this.impostoFormService.createImpostoFormGroup();

  compareEsfera = (o1: IEsfera | null, o2: IEsfera | null): boolean => this.esferaService.compareEsfera(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imposto }) => {
      this.imposto = imposto;
      if (imposto) {
        this.updateForm(imposto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const imposto = this.impostoFormService.getImposto(this.editForm);
    if (imposto.id !== null) {
      this.subscribeToSaveResponse(this.impostoService.update(imposto));
    } else {
      this.subscribeToSaveResponse(this.impostoService.create(imposto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImposto>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(imposto: IImposto): void {
    this.imposto = imposto;
    this.impostoFormService.resetForm(this.editForm, imposto);

    this.esferasSharedCollection = this.esferaService.addEsferaToCollectionIfMissing<IEsfera>(this.esferasSharedCollection, imposto.esfera);
  }

  protected loadRelationshipsOptions(): void {
    this.esferaService
      .query()
      .pipe(map((res: HttpResponse<IEsfera[]>) => res.body ?? []))
      .pipe(map((esferas: IEsfera[]) => this.esferaService.addEsferaToCollectionIfMissing<IEsfera>(esferas, this.imposto?.esfera)))
      .subscribe((esferas: IEsfera[]) => (this.esferasSharedCollection = esferas));
  }
}
