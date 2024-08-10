import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul/service/plano-conta-azul.service';
import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { DescontoPlanoContaAzulService } from '../service/desconto-plano-conta-azul.service';
import { IDescontoPlanoContaAzul } from '../desconto-plano-conta-azul.model';
import { DescontoPlanoContaAzulFormService, DescontoPlanoContaAzulFormGroup } from './desconto-plano-conta-azul-form.service';

@Component({
  standalone: true,
  selector: 'jhi-desconto-plano-conta-azul-update',
  templateUrl: './desconto-plano-conta-azul-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DescontoPlanoContaAzulUpdateComponent implements OnInit {
  isSaving = false;
  descontoPlanoContaAzul: IDescontoPlanoContaAzul | null = null;

  planoContaAzulsSharedCollection: IPlanoContaAzul[] = [];
  periodoPagamentosSharedCollection: IPeriodoPagamento[] = [];

  protected descontoPlanoContaAzulService = inject(DescontoPlanoContaAzulService);
  protected descontoPlanoContaAzulFormService = inject(DescontoPlanoContaAzulFormService);
  protected planoContaAzulService = inject(PlanoContaAzulService);
  protected periodoPagamentoService = inject(PeriodoPagamentoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DescontoPlanoContaAzulFormGroup = this.descontoPlanoContaAzulFormService.createDescontoPlanoContaAzulFormGroup();

  comparePlanoContaAzul = (o1: IPlanoContaAzul | null, o2: IPlanoContaAzul | null): boolean =>
    this.planoContaAzulService.comparePlanoContaAzul(o1, o2);

  comparePeriodoPagamento = (o1: IPeriodoPagamento | null, o2: IPeriodoPagamento | null): boolean =>
    this.periodoPagamentoService.comparePeriodoPagamento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descontoPlanoContaAzul }) => {
      this.descontoPlanoContaAzul = descontoPlanoContaAzul;
      if (descontoPlanoContaAzul) {
        this.updateForm(descontoPlanoContaAzul);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const descontoPlanoContaAzul = this.descontoPlanoContaAzulFormService.getDescontoPlanoContaAzul(this.editForm);
    if (descontoPlanoContaAzul.id !== null) {
      this.subscribeToSaveResponse(this.descontoPlanoContaAzulService.update(descontoPlanoContaAzul));
    } else {
      this.subscribeToSaveResponse(this.descontoPlanoContaAzulService.create(descontoPlanoContaAzul));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDescontoPlanoContaAzul>>): void {
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

  protected updateForm(descontoPlanoContaAzul: IDescontoPlanoContaAzul): void {
    this.descontoPlanoContaAzul = descontoPlanoContaAzul;
    this.descontoPlanoContaAzulFormService.resetForm(this.editForm, descontoPlanoContaAzul);

    this.planoContaAzulsSharedCollection = this.planoContaAzulService.addPlanoContaAzulToCollectionIfMissing<IPlanoContaAzul>(
      this.planoContaAzulsSharedCollection,
      descontoPlanoContaAzul.planoContaAzul,
    );
    this.periodoPagamentosSharedCollection = this.periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing<IPeriodoPagamento>(
      this.periodoPagamentosSharedCollection,
      descontoPlanoContaAzul.periodoPagamento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.planoContaAzulService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContaAzul[]>) => res.body ?? []))
      .pipe(
        map((planoContaAzuls: IPlanoContaAzul[]) =>
          this.planoContaAzulService.addPlanoContaAzulToCollectionIfMissing<IPlanoContaAzul>(
            planoContaAzuls,
            this.descontoPlanoContaAzul?.planoContaAzul,
          ),
        ),
      )
      .subscribe((planoContaAzuls: IPlanoContaAzul[]) => (this.planoContaAzulsSharedCollection = planoContaAzuls));

    this.periodoPagamentoService
      .query()
      .pipe(map((res: HttpResponse<IPeriodoPagamento[]>) => res.body ?? []))
      .pipe(
        map((periodoPagamentos: IPeriodoPagamento[]) =>
          this.periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing<IPeriodoPagamento>(
            periodoPagamentos,
            this.descontoPlanoContaAzul?.periodoPagamento,
          ),
        ),
      )
      .subscribe((periodoPagamentos: IPeriodoPagamento[]) => (this.periodoPagamentosSharedCollection = periodoPagamentos));
  }
}
