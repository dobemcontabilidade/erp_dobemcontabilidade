import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGatewayPagamento } from '../gateway-pagamento.model';
import { GatewayPagamentoService } from '../service/gateway-pagamento.service';
import { GatewayPagamentoFormService, GatewayPagamentoFormGroup } from './gateway-pagamento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-gateway-pagamento-update',
  templateUrl: './gateway-pagamento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GatewayPagamentoUpdateComponent implements OnInit {
  isSaving = false;
  gatewayPagamento: IGatewayPagamento | null = null;

  protected gatewayPagamentoService = inject(GatewayPagamentoService);
  protected gatewayPagamentoFormService = inject(GatewayPagamentoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GatewayPagamentoFormGroup = this.gatewayPagamentoFormService.createGatewayPagamentoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gatewayPagamento }) => {
      this.gatewayPagamento = gatewayPagamento;
      if (gatewayPagamento) {
        this.updateForm(gatewayPagamento);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gatewayPagamento = this.gatewayPagamentoFormService.getGatewayPagamento(this.editForm);
    if (gatewayPagamento.id !== null) {
      this.subscribeToSaveResponse(this.gatewayPagamentoService.update(gatewayPagamento));
    } else {
      this.subscribeToSaveResponse(this.gatewayPagamentoService.create(gatewayPagamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGatewayPagamento>>): void {
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

  protected updateForm(gatewayPagamento: IGatewayPagamento): void {
    this.gatewayPagamento = gatewayPagamento;
    this.gatewayPagamentoFormService.resetForm(this.editForm, gatewayPagamento);
  }
}
