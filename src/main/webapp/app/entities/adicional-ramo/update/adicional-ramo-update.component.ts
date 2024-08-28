import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from 'app/entities/plano-assinatura-contabil/service/plano-assinatura-contabil.service';
import { AdicionalRamoService } from '../service/adicional-ramo.service';
import { IAdicionalRamo } from '../adicional-ramo.model';
import { AdicionalRamoFormService, AdicionalRamoFormGroup } from './adicional-ramo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-adicional-ramo-update',
  templateUrl: './adicional-ramo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AdicionalRamoUpdateComponent implements OnInit {
  isSaving = false;
  adicionalRamo: IAdicionalRamo | null = null;

  ramosSharedCollection: IRamo[] = [];
  planoAssinaturaContabilsSharedCollection: IPlanoAssinaturaContabil[] = [];

  protected adicionalRamoService = inject(AdicionalRamoService);
  protected adicionalRamoFormService = inject(AdicionalRamoFormService);
  protected ramoService = inject(RamoService);
  protected planoAssinaturaContabilService = inject(PlanoAssinaturaContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AdicionalRamoFormGroup = this.adicionalRamoFormService.createAdicionalRamoFormGroup();

  compareRamo = (o1: IRamo | null, o2: IRamo | null): boolean => this.ramoService.compareRamo(o1, o2);

  comparePlanoAssinaturaContabil = (o1: IPlanoAssinaturaContabil | null, o2: IPlanoAssinaturaContabil | null): boolean =>
    this.planoAssinaturaContabilService.comparePlanoAssinaturaContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adicionalRamo }) => {
      this.adicionalRamo = adicionalRamo;
      if (adicionalRamo) {
        this.updateForm(adicionalRamo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adicionalRamo = this.adicionalRamoFormService.getAdicionalRamo(this.editForm);
    if (adicionalRamo.id !== null) {
      this.subscribeToSaveResponse(this.adicionalRamoService.update(adicionalRamo));
    } else {
      this.subscribeToSaveResponse(this.adicionalRamoService.create(adicionalRamo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdicionalRamo>>): void {
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

  protected updateForm(adicionalRamo: IAdicionalRamo): void {
    this.adicionalRamo = adicionalRamo;
    this.adicionalRamoFormService.resetForm(this.editForm, adicionalRamo);

    this.ramosSharedCollection = this.ramoService.addRamoToCollectionIfMissing<IRamo>(this.ramosSharedCollection, adicionalRamo.ramo);
    this.planoAssinaturaContabilsSharedCollection =
      this.planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing<IPlanoAssinaturaContabil>(
        this.planoAssinaturaContabilsSharedCollection,
        adicionalRamo.planoAssinaturaContabil,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.ramoService
      .query()
      .pipe(map((res: HttpResponse<IRamo[]>) => res.body ?? []))
      .pipe(map((ramos: IRamo[]) => this.ramoService.addRamoToCollectionIfMissing<IRamo>(ramos, this.adicionalRamo?.ramo)))
      .subscribe((ramos: IRamo[]) => (this.ramosSharedCollection = ramos));

    this.planoAssinaturaContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoAssinaturaContabil[]>) => res.body ?? []))
      .pipe(
        map((planoAssinaturaContabils: IPlanoAssinaturaContabil[]) =>
          this.planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing<IPlanoAssinaturaContabil>(
            planoAssinaturaContabils,
            this.adicionalRamo?.planoAssinaturaContabil,
          ),
        ),
      )
      .subscribe(
        (planoAssinaturaContabils: IPlanoAssinaturaContabil[]) =>
          (this.planoAssinaturaContabilsSharedCollection = planoAssinaturaContabils),
      );
  }
}
