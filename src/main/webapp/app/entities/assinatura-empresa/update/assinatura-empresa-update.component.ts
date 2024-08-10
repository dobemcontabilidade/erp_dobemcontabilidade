import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IFormaDePagamento } from 'app/entities/forma-de-pagamento/forma-de-pagamento.model';
import { FormaDePagamentoService } from 'app/entities/forma-de-pagamento/service/forma-de-pagamento.service';
import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul/service/plano-conta-azul.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { SituacaoContratoEmpresaEnum } from 'app/entities/enumerations/situacao-contrato-empresa-enum.model';
import { TipoContratoEnum } from 'app/entities/enumerations/tipo-contrato-enum.model';
import { AssinaturaEmpresaService } from '../service/assinatura-empresa.service';
import { IAssinaturaEmpresa } from '../assinatura-empresa.model';
import { AssinaturaEmpresaFormService, AssinaturaEmpresaFormGroup } from './assinatura-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-assinatura-empresa-update',
  templateUrl: './assinatura-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AssinaturaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  assinaturaEmpresa: IAssinaturaEmpresa | null = null;
  situacaoContratoEmpresaEnumValues = Object.keys(SituacaoContratoEmpresaEnum);
  tipoContratoEnumValues = Object.keys(TipoContratoEnum);

  periodoPagamentosSharedCollection: IPeriodoPagamento[] = [];
  formaDePagamentosSharedCollection: IFormaDePagamento[] = [];
  planoContaAzulsSharedCollection: IPlanoContaAzul[] = [];
  planoContabilsSharedCollection: IPlanoContabil[] = [];
  empresasSharedCollection: IEmpresa[] = [];

  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected assinaturaEmpresaFormService = inject(AssinaturaEmpresaFormService);
  protected periodoPagamentoService = inject(PeriodoPagamentoService);
  protected formaDePagamentoService = inject(FormaDePagamentoService);
  protected planoContaAzulService = inject(PlanoContaAzulService);
  protected planoContabilService = inject(PlanoContabilService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AssinaturaEmpresaFormGroup = this.assinaturaEmpresaFormService.createAssinaturaEmpresaFormGroup();

  comparePeriodoPagamento = (o1: IPeriodoPagamento | null, o2: IPeriodoPagamento | null): boolean =>
    this.periodoPagamentoService.comparePeriodoPagamento(o1, o2);

  compareFormaDePagamento = (o1: IFormaDePagamento | null, o2: IFormaDePagamento | null): boolean =>
    this.formaDePagamentoService.compareFormaDePagamento(o1, o2);

  comparePlanoContaAzul = (o1: IPlanoContaAzul | null, o2: IPlanoContaAzul | null): boolean =>
    this.planoContaAzulService.comparePlanoContaAzul(o1, o2);

  comparePlanoContabil = (o1: IPlanoContabil | null, o2: IPlanoContabil | null): boolean =>
    this.planoContabilService.comparePlanoContabil(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assinaturaEmpresa }) => {
      this.assinaturaEmpresa = assinaturaEmpresa;
      if (assinaturaEmpresa) {
        this.updateForm(assinaturaEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assinaturaEmpresa = this.assinaturaEmpresaFormService.getAssinaturaEmpresa(this.editForm);
    if (assinaturaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.assinaturaEmpresaService.update(assinaturaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.assinaturaEmpresaService.create(assinaturaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssinaturaEmpresa>>): void {
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

  protected updateForm(assinaturaEmpresa: IAssinaturaEmpresa): void {
    this.assinaturaEmpresa = assinaturaEmpresa;
    this.assinaturaEmpresaFormService.resetForm(this.editForm, assinaturaEmpresa);

    this.periodoPagamentosSharedCollection = this.periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing<IPeriodoPagamento>(
      this.periodoPagamentosSharedCollection,
      assinaturaEmpresa.periodoPagamento,
    );
    this.formaDePagamentosSharedCollection = this.formaDePagamentoService.addFormaDePagamentoToCollectionIfMissing<IFormaDePagamento>(
      this.formaDePagamentosSharedCollection,
      assinaturaEmpresa.formaDePagamento,
    );
    this.planoContaAzulsSharedCollection = this.planoContaAzulService.addPlanoContaAzulToCollectionIfMissing<IPlanoContaAzul>(
      this.planoContaAzulsSharedCollection,
      assinaturaEmpresa.planoContaAzul,
    );
    this.planoContabilsSharedCollection = this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
      this.planoContabilsSharedCollection,
      assinaturaEmpresa.planoContabil,
    );
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      assinaturaEmpresa.empresa,
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
            this.assinaturaEmpresa?.periodoPagamento,
          ),
        ),
      )
      .subscribe((periodoPagamentos: IPeriodoPagamento[]) => (this.periodoPagamentosSharedCollection = periodoPagamentos));

    this.formaDePagamentoService
      .query()
      .pipe(map((res: HttpResponse<IFormaDePagamento[]>) => res.body ?? []))
      .pipe(
        map((formaDePagamentos: IFormaDePagamento[]) =>
          this.formaDePagamentoService.addFormaDePagamentoToCollectionIfMissing<IFormaDePagamento>(
            formaDePagamentos,
            this.assinaturaEmpresa?.formaDePagamento,
          ),
        ),
      )
      .subscribe((formaDePagamentos: IFormaDePagamento[]) => (this.formaDePagamentosSharedCollection = formaDePagamentos));

    this.planoContaAzulService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContaAzul[]>) => res.body ?? []))
      .pipe(
        map((planoContaAzuls: IPlanoContaAzul[]) =>
          this.planoContaAzulService.addPlanoContaAzulToCollectionIfMissing<IPlanoContaAzul>(
            planoContaAzuls,
            this.assinaturaEmpresa?.planoContaAzul,
          ),
        ),
      )
      .subscribe((planoContaAzuls: IPlanoContaAzul[]) => (this.planoContaAzulsSharedCollection = planoContaAzuls));

    this.planoContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContabil[]>) => res.body ?? []))
      .pipe(
        map((planoContabils: IPlanoContabil[]) =>
          this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
            planoContabils,
            this.assinaturaEmpresa?.planoContabil,
          ),
        ),
      )
      .subscribe((planoContabils: IPlanoContabil[]) => (this.planoContabilsSharedCollection = planoContabils));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.assinaturaEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
