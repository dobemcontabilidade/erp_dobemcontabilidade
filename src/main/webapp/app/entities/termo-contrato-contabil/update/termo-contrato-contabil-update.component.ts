import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { ITermoContratoContabil } from '../termo-contrato-contabil.model';
import { TermoContratoContabilService } from '../service/termo-contrato-contabil.service';
import { TermoContratoContabilFormService, TermoContratoContabilFormGroup } from './termo-contrato-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-termo-contrato-contabil-update',
  templateUrl: './termo-contrato-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TermoContratoContabilUpdateComponent implements OnInit {
  isSaving = false;
  termoContratoContabil: ITermoContratoContabil | null = null;

  planoContabilsSharedCollection: IPlanoContabil[] = [];

  protected termoContratoContabilService = inject(TermoContratoContabilService);
  protected termoContratoContabilFormService = inject(TermoContratoContabilFormService);
  protected planoContabilService = inject(PlanoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TermoContratoContabilFormGroup = this.termoContratoContabilFormService.createTermoContratoContabilFormGroup();

  comparePlanoContabil = (o1: IPlanoContabil | null, o2: IPlanoContabil | null): boolean =>
    this.planoContabilService.comparePlanoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ termoContratoContabil }) => {
      this.termoContratoContabil = termoContratoContabil;
      if (termoContratoContabil) {
        this.updateForm(termoContratoContabil);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const termoContratoContabil = this.termoContratoContabilFormService.getTermoContratoContabil(this.editForm);
    if (termoContratoContabil.id !== null) {
      this.subscribeToSaveResponse(this.termoContratoContabilService.update(termoContratoContabil));
    } else {
      this.subscribeToSaveResponse(this.termoContratoContabilService.create(termoContratoContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITermoContratoContabil>>): void {
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

  protected updateForm(termoContratoContabil: ITermoContratoContabil): void {
    this.termoContratoContabil = termoContratoContabil;
    this.termoContratoContabilFormService.resetForm(this.editForm, termoContratoContabil);

    this.planoContabilsSharedCollection = this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
      this.planoContabilsSharedCollection,
      termoContratoContabil.planoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.planoContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContabil[]>) => res.body ?? []))
      .pipe(
        map((planoContabils: IPlanoContabil[]) =>
          this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
            planoContabils,
            this.termoContratoContabil?.planoContabil,
          ),
        ),
      )
      .subscribe((planoContabils: IPlanoContabil[]) => (this.planoContabilsSharedCollection = planoContabils));
  }
}
