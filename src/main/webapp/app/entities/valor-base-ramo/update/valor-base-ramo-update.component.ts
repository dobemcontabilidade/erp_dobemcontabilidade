import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { ValorBaseRamoService } from '../service/valor-base-ramo.service';
import { IValorBaseRamo } from '../valor-base-ramo.model';
import { ValorBaseRamoFormService, ValorBaseRamoFormGroup } from './valor-base-ramo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-valor-base-ramo-update',
  templateUrl: './valor-base-ramo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ValorBaseRamoUpdateComponent implements OnInit {
  isSaving = false;
  valorBaseRamo: IValorBaseRamo | null = null;

  ramosSharedCollection: IRamo[] = [];
  planoContabilsSharedCollection: IPlanoContabil[] = [];

  protected valorBaseRamoService = inject(ValorBaseRamoService);
  protected valorBaseRamoFormService = inject(ValorBaseRamoFormService);
  protected ramoService = inject(RamoService);
  protected planoContabilService = inject(PlanoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ValorBaseRamoFormGroup = this.valorBaseRamoFormService.createValorBaseRamoFormGroup();

  compareRamo = (o1: IRamo | null, o2: IRamo | null): boolean => this.ramoService.compareRamo(o1, o2);

  comparePlanoContabil = (o1: IPlanoContabil | null, o2: IPlanoContabil | null): boolean =>
    this.planoContabilService.comparePlanoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ valorBaseRamo }) => {
      this.valorBaseRamo = valorBaseRamo;
      if (valorBaseRamo) {
        this.updateForm(valorBaseRamo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const valorBaseRamo = this.valorBaseRamoFormService.getValorBaseRamo(this.editForm);
    if (valorBaseRamo.id !== null) {
      this.subscribeToSaveResponse(this.valorBaseRamoService.update(valorBaseRamo));
    } else {
      this.subscribeToSaveResponse(this.valorBaseRamoService.create(valorBaseRamo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IValorBaseRamo>>): void {
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

  protected updateForm(valorBaseRamo: IValorBaseRamo): void {
    this.valorBaseRamo = valorBaseRamo;
    this.valorBaseRamoFormService.resetForm(this.editForm, valorBaseRamo);

    this.ramosSharedCollection = this.ramoService.addRamoToCollectionIfMissing<IRamo>(this.ramosSharedCollection, valorBaseRamo.ramo);
    this.planoContabilsSharedCollection = this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
      this.planoContabilsSharedCollection,
      valorBaseRamo.planoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ramoService
      .query()
      .pipe(map((res: HttpResponse<IRamo[]>) => res.body ?? []))
      .pipe(map((ramos: IRamo[]) => this.ramoService.addRamoToCollectionIfMissing<IRamo>(ramos, this.valorBaseRamo?.ramo)))
      .subscribe((ramos: IRamo[]) => (this.ramosSharedCollection = ramos));

    this.planoContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContabil[]>) => res.body ?? []))
      .pipe(
        map((planoContabils: IPlanoContabil[]) =>
          this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
            planoContabils,
            this.valorBaseRamo?.planoContabil,
          ),
        ),
      )
      .subscribe((planoContabils: IPlanoContabil[]) => (this.planoContabilsSharedCollection = planoContabils));
  }
}
