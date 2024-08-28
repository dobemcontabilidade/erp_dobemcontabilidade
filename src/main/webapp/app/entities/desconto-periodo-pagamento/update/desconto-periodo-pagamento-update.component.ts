import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from 'app/entities/plano-assinatura-contabil/service/plano-assinatura-contabil.service';
import { DescontoPeriodoPagamentoService } from '../service/desconto-periodo-pagamento.service';
import { IDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';
import { DescontoPeriodoPagamentoFormService, DescontoPeriodoPagamentoFormGroup } from './desconto-periodo-pagamento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-desconto-periodo-pagamento-update',
  templateUrl: './desconto-periodo-pagamento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DescontoPeriodoPagamentoUpdateComponent implements OnInit {
  isSaving = false;
  descontoPeriodoPagamento: IDescontoPeriodoPagamento | null = null;

  periodoPagamentosSharedCollection: IPeriodoPagamento[] = [];
  planoAssinaturaContabilsSharedCollection: IPlanoAssinaturaContabil[] = [];

  protected descontoPeriodoPagamentoService = inject(DescontoPeriodoPagamentoService);
  protected descontoPeriodoPagamentoFormService = inject(DescontoPeriodoPagamentoFormService);
  protected periodoPagamentoService = inject(PeriodoPagamentoService);
  protected planoAssinaturaContabilService = inject(PlanoAssinaturaContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DescontoPeriodoPagamentoFormGroup = this.descontoPeriodoPagamentoFormService.createDescontoPeriodoPagamentoFormGroup();

  comparePeriodoPagamento = (o1: IPeriodoPagamento | null, o2: IPeriodoPagamento | null): boolean =>
    this.periodoPagamentoService.comparePeriodoPagamento(o1, o2);

  comparePlanoAssinaturaContabil = (o1: IPlanoAssinaturaContabil | null, o2: IPlanoAssinaturaContabil | null): boolean =>
    this.planoAssinaturaContabilService.comparePlanoAssinaturaContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descontoPeriodoPagamento }) => {
      this.descontoPeriodoPagamento = descontoPeriodoPagamento;
      if (descontoPeriodoPagamento) {
        this.updateForm(descontoPeriodoPagamento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const descontoPeriodoPagamento = this.descontoPeriodoPagamentoFormService.getDescontoPeriodoPagamento(this.editForm);
    if (descontoPeriodoPagamento.id !== null) {
      this.subscribeToSaveResponse(this.descontoPeriodoPagamentoService.update(descontoPeriodoPagamento));
    } else {
      this.subscribeToSaveResponse(this.descontoPeriodoPagamentoService.create(descontoPeriodoPagamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDescontoPeriodoPagamento>>): void {
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

  protected updateForm(descontoPeriodoPagamento: IDescontoPeriodoPagamento): void {
    this.descontoPeriodoPagamento = descontoPeriodoPagamento;
    this.descontoPeriodoPagamentoFormService.resetForm(this.editForm, descontoPeriodoPagamento);

    this.periodoPagamentosSharedCollection = this.periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing<IPeriodoPagamento>(
      this.periodoPagamentosSharedCollection,
      descontoPeriodoPagamento.periodoPagamento,
    );
    this.planoAssinaturaContabilsSharedCollection =
      this.planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing<IPlanoAssinaturaContabil>(
        this.planoAssinaturaContabilsSharedCollection,
        descontoPeriodoPagamento.planoAssinaturaContabil,
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
            this.descontoPeriodoPagamento?.periodoPagamento,
          ),
        ),
      )
      .subscribe((periodoPagamentos: IPeriodoPagamento[]) => (this.periodoPagamentosSharedCollection = periodoPagamentos));

    this.planoAssinaturaContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoAssinaturaContabil[]>) => res.body ?? []))
      .pipe(
        map((planoAssinaturaContabils: IPlanoAssinaturaContabil[]) =>
          this.planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing<IPlanoAssinaturaContabil>(
            planoAssinaturaContabils,
            this.descontoPeriodoPagamento?.planoAssinaturaContabil,
          ),
        ),
      )
      .subscribe(
        (planoAssinaturaContabils: IPlanoAssinaturaContabil[]) =>
          (this.planoAssinaturaContabilsSharedCollection = planoAssinaturaContabils),
      );
  }
}
