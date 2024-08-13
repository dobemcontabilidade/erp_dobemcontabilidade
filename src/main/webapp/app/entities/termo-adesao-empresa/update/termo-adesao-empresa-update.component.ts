import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { TermoAdesaoEmpresaService } from '../service/termo-adesao-empresa.service';
import { ITermoAdesaoEmpresa } from '../termo-adesao-empresa.model';
import { TermoAdesaoEmpresaFormService, TermoAdesaoEmpresaFormGroup } from './termo-adesao-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-termo-adesao-empresa-update',
  templateUrl: './termo-adesao-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TermoAdesaoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  termoAdesaoEmpresa: ITermoAdesaoEmpresa | null = null;

  empresasSharedCollection: IEmpresa[] = [];
  planoContabilsSharedCollection: IPlanoContabil[] = [];

  protected termoAdesaoEmpresaService = inject(TermoAdesaoEmpresaService);
  protected termoAdesaoEmpresaFormService = inject(TermoAdesaoEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected planoContabilService = inject(PlanoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TermoAdesaoEmpresaFormGroup = this.termoAdesaoEmpresaFormService.createTermoAdesaoEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  comparePlanoContabil = (o1: IPlanoContabil | null, o2: IPlanoContabil | null): boolean =>
    this.planoContabilService.comparePlanoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ termoAdesaoEmpresa }) => {
      this.termoAdesaoEmpresa = termoAdesaoEmpresa;
      if (termoAdesaoEmpresa) {
        this.updateForm(termoAdesaoEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const termoAdesaoEmpresa = this.termoAdesaoEmpresaFormService.getTermoAdesaoEmpresa(this.editForm);
    if (termoAdesaoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.termoAdesaoEmpresaService.update(termoAdesaoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.termoAdesaoEmpresaService.create(termoAdesaoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITermoAdesaoEmpresa>>): void {
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

  protected updateForm(termoAdesaoEmpresa: ITermoAdesaoEmpresa): void {
    this.termoAdesaoEmpresa = termoAdesaoEmpresa;
    this.termoAdesaoEmpresaFormService.resetForm(this.editForm, termoAdesaoEmpresa);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      termoAdesaoEmpresa.empresa,
    );
    this.planoContabilsSharedCollection = this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
      this.planoContabilsSharedCollection,
      termoAdesaoEmpresa.planoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.termoAdesaoEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.planoContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContabil[]>) => res.body ?? []))
      .pipe(
        map((planoContabils: IPlanoContabil[]) =>
          this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
            planoContabils,
            this.termoAdesaoEmpresa?.planoContabil,
          ),
        ),
      )
      .subscribe((planoContabils: IPlanoContabil[]) => (this.planoContabilsSharedCollection = planoContabils));
  }
}
