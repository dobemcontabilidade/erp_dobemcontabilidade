import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { ImpostoEmpresaService } from '../service/imposto-empresa.service';
import { IImpostoEmpresa } from '../imposto-empresa.model';
import { ImpostoEmpresaFormService, ImpostoEmpresaFormGroup } from './imposto-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-imposto-empresa-update',
  templateUrl: './imposto-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ImpostoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  impostoEmpresa: IImpostoEmpresa | null = null;

  empresasSharedCollection: IEmpresa[] = [];
  impostosSharedCollection: IImposto[] = [];

  protected impostoEmpresaService = inject(ImpostoEmpresaService);
  protected impostoEmpresaFormService = inject(ImpostoEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected impostoService = inject(ImpostoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ImpostoEmpresaFormGroup = this.impostoEmpresaFormService.createImpostoEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareImposto = (o1: IImposto | null, o2: IImposto | null): boolean => this.impostoService.compareImposto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ impostoEmpresa }) => {
      this.impostoEmpresa = impostoEmpresa;
      if (impostoEmpresa) {
        this.updateForm(impostoEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const impostoEmpresa = this.impostoEmpresaFormService.getImpostoEmpresa(this.editForm);
    if (impostoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.impostoEmpresaService.update(impostoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.impostoEmpresaService.create(impostoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImpostoEmpresa>>): void {
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

  protected updateForm(impostoEmpresa: IImpostoEmpresa): void {
    this.impostoEmpresa = impostoEmpresa;
    this.impostoEmpresaFormService.resetForm(this.editForm, impostoEmpresa);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      impostoEmpresa.empresa,
    );
    this.impostosSharedCollection = this.impostoService.addImpostoToCollectionIfMissing<IImposto>(
      this.impostosSharedCollection,
      impostoEmpresa.imposto,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.impostoEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.impostoService
      .query()
      .pipe(map((res: HttpResponse<IImposto[]>) => res.body ?? []))
      .pipe(
        map((impostos: IImposto[]) =>
          this.impostoService.addImpostoToCollectionIfMissing<IImposto>(impostos, this.impostoEmpresa?.imposto),
        ),
      )
      .subscribe((impostos: IImposto[]) => (this.impostosSharedCollection = impostos));
  }
}
