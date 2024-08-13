import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { ImpostoEmpresaModeloService } from '../service/imposto-empresa-modelo.service';
import { IImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';
import { ImpostoEmpresaModeloFormService, ImpostoEmpresaModeloFormGroup } from './imposto-empresa-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-imposto-empresa-modelo-update',
  templateUrl: './imposto-empresa-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ImpostoEmpresaModeloUpdateComponent implements OnInit {
  isSaving = false;
  impostoEmpresaModelo: IImpostoEmpresaModelo | null = null;

  empresaModelosSharedCollection: IEmpresaModelo[] = [];
  impostosSharedCollection: IImposto[] = [];

  protected impostoEmpresaModeloService = inject(ImpostoEmpresaModeloService);
  protected impostoEmpresaModeloFormService = inject(ImpostoEmpresaModeloFormService);
  protected empresaModeloService = inject(EmpresaModeloService);
  protected impostoService = inject(ImpostoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ImpostoEmpresaModeloFormGroup = this.impostoEmpresaModeloFormService.createImpostoEmpresaModeloFormGroup();

  compareEmpresaModelo = (o1: IEmpresaModelo | null, o2: IEmpresaModelo | null): boolean =>
    this.empresaModeloService.compareEmpresaModelo(o1, o2);

  compareImposto = (o1: IImposto | null, o2: IImposto | null): boolean => this.impostoService.compareImposto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ impostoEmpresaModelo }) => {
      this.impostoEmpresaModelo = impostoEmpresaModelo;
      if (impostoEmpresaModelo) {
        this.updateForm(impostoEmpresaModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const impostoEmpresaModelo = this.impostoEmpresaModeloFormService.getImpostoEmpresaModelo(this.editForm);
    if (impostoEmpresaModelo.id !== null) {
      this.subscribeToSaveResponse(this.impostoEmpresaModeloService.update(impostoEmpresaModelo));
    } else {
      this.subscribeToSaveResponse(this.impostoEmpresaModeloService.create(impostoEmpresaModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImpostoEmpresaModelo>>): void {
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

  protected updateForm(impostoEmpresaModelo: IImpostoEmpresaModelo): void {
    this.impostoEmpresaModelo = impostoEmpresaModelo;
    this.impostoEmpresaModeloFormService.resetForm(this.editForm, impostoEmpresaModelo);

    this.empresaModelosSharedCollection = this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
      this.empresaModelosSharedCollection,
      impostoEmpresaModelo.empresaModelo,
    );
    this.impostosSharedCollection = this.impostoService.addImpostoToCollectionIfMissing<IImposto>(
      this.impostosSharedCollection,
      impostoEmpresaModelo.imposto,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaModeloService
      .query()
      .pipe(map((res: HttpResponse<IEmpresaModelo[]>) => res.body ?? []))
      .pipe(
        map((empresaModelos: IEmpresaModelo[]) =>
          this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
            empresaModelos,
            this.impostoEmpresaModelo?.empresaModelo,
          ),
        ),
      )
      .subscribe((empresaModelos: IEmpresaModelo[]) => (this.empresaModelosSharedCollection = empresaModelos));

    this.impostoService
      .query()
      .pipe(map((res: HttpResponse<IImposto[]>) => res.body ?? []))
      .pipe(
        map((impostos: IImposto[]) =>
          this.impostoService.addImpostoToCollectionIfMissing<IImposto>(impostos, this.impostoEmpresaModelo?.imposto),
        ),
      )
      .subscribe((impostos: IImposto[]) => (this.impostosSharedCollection = impostos));
  }
}
