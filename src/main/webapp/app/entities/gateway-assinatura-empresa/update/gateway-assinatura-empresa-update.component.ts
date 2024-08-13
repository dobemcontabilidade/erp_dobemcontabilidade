import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IGatewayPagamento } from 'app/entities/gateway-pagamento/gateway-pagamento.model';
import { GatewayPagamentoService } from 'app/entities/gateway-pagamento/service/gateway-pagamento.service';
import { GatewayAssinaturaEmpresaService } from '../service/gateway-assinatura-empresa.service';
import { IGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';
import { GatewayAssinaturaEmpresaFormService, GatewayAssinaturaEmpresaFormGroup } from './gateway-assinatura-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-gateway-assinatura-empresa-update',
  templateUrl: './gateway-assinatura-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GatewayAssinaturaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa | null = null;

  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];
  gatewayPagamentosSharedCollection: IGatewayPagamento[] = [];

  protected gatewayAssinaturaEmpresaService = inject(GatewayAssinaturaEmpresaService);
  protected gatewayAssinaturaEmpresaFormService = inject(GatewayAssinaturaEmpresaFormService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected gatewayPagamentoService = inject(GatewayPagamentoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GatewayAssinaturaEmpresaFormGroup = this.gatewayAssinaturaEmpresaFormService.createGatewayAssinaturaEmpresaFormGroup();

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  compareGatewayPagamento = (o1: IGatewayPagamento | null, o2: IGatewayPagamento | null): boolean =>
    this.gatewayPagamentoService.compareGatewayPagamento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gatewayAssinaturaEmpresa }) => {
      this.gatewayAssinaturaEmpresa = gatewayAssinaturaEmpresa;
      if (gatewayAssinaturaEmpresa) {
        this.updateForm(gatewayAssinaturaEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gatewayAssinaturaEmpresa = this.gatewayAssinaturaEmpresaFormService.getGatewayAssinaturaEmpresa(this.editForm);
    if (gatewayAssinaturaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.gatewayAssinaturaEmpresaService.update(gatewayAssinaturaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.gatewayAssinaturaEmpresaService.create(gatewayAssinaturaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGatewayAssinaturaEmpresa>>): void {
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

  protected updateForm(gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa): void {
    this.gatewayAssinaturaEmpresa = gatewayAssinaturaEmpresa;
    this.gatewayAssinaturaEmpresaFormService.resetForm(this.editForm, gatewayAssinaturaEmpresa);

    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      gatewayAssinaturaEmpresa.assinaturaEmpresa,
    );
    this.gatewayPagamentosSharedCollection = this.gatewayPagamentoService.addGatewayPagamentoToCollectionIfMissing<IGatewayPagamento>(
      this.gatewayPagamentosSharedCollection,
      gatewayAssinaturaEmpresa.gatewayPagamento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.gatewayAssinaturaEmpresa?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));

    this.gatewayPagamentoService
      .query()
      .pipe(map((res: HttpResponse<IGatewayPagamento[]>) => res.body ?? []))
      .pipe(
        map((gatewayPagamentos: IGatewayPagamento[]) =>
          this.gatewayPagamentoService.addGatewayPagamentoToCollectionIfMissing<IGatewayPagamento>(
            gatewayPagamentos,
            this.gatewayAssinaturaEmpresa?.gatewayPagamento,
          ),
        ),
      )
      .subscribe((gatewayPagamentos: IGatewayPagamento[]) => (this.gatewayPagamentosSharedCollection = gatewayPagamentos));
  }
}
