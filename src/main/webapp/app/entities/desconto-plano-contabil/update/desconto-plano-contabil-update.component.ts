import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { DescontoPlanoContabilService } from '../service/desconto-plano-contabil.service';
import { IDescontoPlanoContabil } from '../desconto-plano-contabil.model';
import { DescontoPlanoContabilFormService, DescontoPlanoContabilFormGroup } from './desconto-plano-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-desconto-plano-contabil-update',
  templateUrl: './desconto-plano-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DescontoPlanoContabilUpdateComponent implements OnInit {
  isSaving = false;
  descontoPlanoContabil: IDescontoPlanoContabil | null = null;

  periodoPagamentosSharedCollection: IPeriodoPagamento[] = [];
  planoContabilsSharedCollection: IPlanoContabil[] = [];

  protected descontoPlanoContabilService = inject(DescontoPlanoContabilService);
  protected descontoPlanoContabilFormService = inject(DescontoPlanoContabilFormService);
  protected periodoPagamentoService = inject(PeriodoPagamentoService);
  protected planoContabilService = inject(PlanoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DescontoPlanoContabilFormGroup = this.descontoPlanoContabilFormService.createDescontoPlanoContabilFormGroup();

  comparePeriodoPagamento = (o1: IPeriodoPagamento | null, o2: IPeriodoPagamento | null): boolean =>
    this.periodoPagamentoService.comparePeriodoPagamento(o1, o2);

  comparePlanoContabil = (o1: IPlanoContabil | null, o2: IPlanoContabil | null): boolean =>
    this.planoContabilService.comparePlanoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descontoPlanoContabil }) => {
      this.descontoPlanoContabil = descontoPlanoContabil;
      if (descontoPlanoContabil) {
        this.updateForm(descontoPlanoContabil);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const descontoPlanoContabil = this.descontoPlanoContabilFormService.getDescontoPlanoContabil(this.editForm);
    if (descontoPlanoContabil.id !== null) {
      this.subscribeToSaveResponse(this.descontoPlanoContabilService.update(descontoPlanoContabil));
    } else {
      this.subscribeToSaveResponse(this.descontoPlanoContabilService.create(descontoPlanoContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDescontoPlanoContabil>>): void {
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

  protected updateForm(descontoPlanoContabil: IDescontoPlanoContabil): void {
    this.descontoPlanoContabil = descontoPlanoContabil;
    this.descontoPlanoContabilFormService.resetForm(this.editForm, descontoPlanoContabil);

    this.periodoPagamentosSharedCollection = this.periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing<IPeriodoPagamento>(
      this.periodoPagamentosSharedCollection,
      descontoPlanoContabil.periodoPagamento,
    );
    this.planoContabilsSharedCollection = this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
      this.planoContabilsSharedCollection,
      descontoPlanoContabil.planoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.periodoPagamentoService
      .query()
      .pipe(map((res: HttpResponse<IPeriodoPagamento[]>) => res.body ?? []))
      .pipe(
        map((periodoPagamentos: IPeriodoPagamento[]) =>
          this.periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing<IPeriodoPagamento>(
            periodoPagamentos,
            this.descontoPlanoContabil?.periodoPagamento,
          ),
        ),
      )
      .subscribe((periodoPagamentos: IPeriodoPagamento[]) => (this.periodoPagamentosSharedCollection = periodoPagamentos));

    this.planoContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContabil[]>) => res.body ?? []))
      .pipe(
        map((planoContabils: IPlanoContabil[]) =>
          this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
            planoContabils,
            this.descontoPlanoContabil?.planoContabil,
          ),
        ),
      )
      .subscribe((planoContabils: IPlanoContabil[]) => (this.planoContabilsSharedCollection = planoContabils));
  }
}
