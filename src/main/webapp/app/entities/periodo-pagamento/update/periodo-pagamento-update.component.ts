import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPeriodoPagamento } from '../periodo-pagamento.model';
import { PeriodoPagamentoService } from '../service/periodo-pagamento.service';
import { PeriodoPagamentoFormService, PeriodoPagamentoFormGroup } from './periodo-pagamento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-periodo-pagamento-update',
  templateUrl: './periodo-pagamento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PeriodoPagamentoUpdateComponent implements OnInit {
  isSaving = false;
  periodoPagamento: IPeriodoPagamento | null = null;

  protected periodoPagamentoService = inject(PeriodoPagamentoService);
  protected periodoPagamentoFormService = inject(PeriodoPagamentoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PeriodoPagamentoFormGroup = this.periodoPagamentoFormService.createPeriodoPagamentoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodoPagamento }) => {
      this.periodoPagamento = periodoPagamento;
      if (periodoPagamento) {
        this.updateForm(periodoPagamento);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const periodoPagamento = this.periodoPagamentoFormService.getPeriodoPagamento(this.editForm);
    if (periodoPagamento.id !== null) {
      this.subscribeToSaveResponse(this.periodoPagamentoService.update(periodoPagamento));
    } else {
      this.subscribeToSaveResponse(this.periodoPagamentoService.create(periodoPagamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodoPagamento>>): void {
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

  protected updateForm(periodoPagamento: IPeriodoPagamento): void {
    this.periodoPagamento = periodoPagamento;
    this.periodoPagamentoFormService.resetForm(this.editForm, periodoPagamento);
  }
}
