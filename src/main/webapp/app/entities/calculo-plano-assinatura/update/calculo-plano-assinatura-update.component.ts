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
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IDescontoPlanoContabil } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from 'app/entities/desconto-plano-contabil/service/desconto-plano-contabil.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { CalculoPlanoAssinaturaService } from '../service/calculo-plano-assinatura.service';
import { ICalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';
import { CalculoPlanoAssinaturaFormService, CalculoPlanoAssinaturaFormGroup } from './calculo-plano-assinatura-form.service';

@Component({
  standalone: true,
  selector: 'jhi-calculo-plano-assinatura-update',
  templateUrl: './calculo-plano-assinatura-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CalculoPlanoAssinaturaUpdateComponent implements OnInit {
  isSaving = false;
  calculoPlanoAssinatura: ICalculoPlanoAssinatura | null = null;

  periodoPagamentosSharedCollection: IPeriodoPagamento[] = [];
  planoContabilsSharedCollection: IPlanoContabil[] = [];
  ramosSharedCollection: IRamo[] = [];
  tributacaosSharedCollection: ITributacao[] = [];
  descontoPlanoContabilsSharedCollection: IDescontoPlanoContabil[] = [];
  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];

  protected calculoPlanoAssinaturaService = inject(CalculoPlanoAssinaturaService);
  protected calculoPlanoAssinaturaFormService = inject(CalculoPlanoAssinaturaFormService);
  protected periodoPagamentoService = inject(PeriodoPagamentoService);
  protected planoContabilService = inject(PlanoContabilService);
  protected ramoService = inject(RamoService);
  protected tributacaoService = inject(TributacaoService);
  protected descontoPlanoContabilService = inject(DescontoPlanoContabilService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CalculoPlanoAssinaturaFormGroup = this.calculoPlanoAssinaturaFormService.createCalculoPlanoAssinaturaFormGroup();

  comparePeriodoPagamento = (o1: IPeriodoPagamento | null, o2: IPeriodoPagamento | null): boolean =>
    this.periodoPagamentoService.comparePeriodoPagamento(o1, o2);

  comparePlanoContabil = (o1: IPlanoContabil | null, o2: IPlanoContabil | null): boolean =>
    this.planoContabilService.comparePlanoContabil(o1, o2);

  compareRamo = (o1: IRamo | null, o2: IRamo | null): boolean => this.ramoService.compareRamo(o1, o2);

  compareTributacao = (o1: ITributacao | null, o2: ITributacao | null): boolean => this.tributacaoService.compareTributacao(o1, o2);

  compareDescontoPlanoContabil = (o1: IDescontoPlanoContabil | null, o2: IDescontoPlanoContabil | null): boolean =>
    this.descontoPlanoContabilService.compareDescontoPlanoContabil(o1, o2);

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calculoPlanoAssinatura }) => {
      this.calculoPlanoAssinatura = calculoPlanoAssinatura;
      if (calculoPlanoAssinatura) {
        this.updateForm(calculoPlanoAssinatura);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calculoPlanoAssinatura = this.calculoPlanoAssinaturaFormService.getCalculoPlanoAssinatura(this.editForm);
    if (calculoPlanoAssinatura.id !== null) {
      this.subscribeToSaveResponse(this.calculoPlanoAssinaturaService.update(calculoPlanoAssinatura));
    } else {
      this.subscribeToSaveResponse(this.calculoPlanoAssinaturaService.create(calculoPlanoAssinatura));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalculoPlanoAssinatura>>): void {
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

  protected updateForm(calculoPlanoAssinatura: ICalculoPlanoAssinatura): void {
    this.calculoPlanoAssinatura = calculoPlanoAssinatura;
    this.calculoPlanoAssinaturaFormService.resetForm(this.editForm, calculoPlanoAssinatura);

    this.periodoPagamentosSharedCollection = this.periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing<IPeriodoPagamento>(
      this.periodoPagamentosSharedCollection,
      calculoPlanoAssinatura.periodoPagamento,
    );
    this.planoContabilsSharedCollection = this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
      this.planoContabilsSharedCollection,
      calculoPlanoAssinatura.planoContabil,
    );
    this.ramosSharedCollection = this.ramoService.addRamoToCollectionIfMissing<IRamo>(
      this.ramosSharedCollection,
      calculoPlanoAssinatura.ramo,
    );
    this.tributacaosSharedCollection = this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(
      this.tributacaosSharedCollection,
      calculoPlanoAssinatura.tributacao,
    );
    this.descontoPlanoContabilsSharedCollection =
      this.descontoPlanoContabilService.addDescontoPlanoContabilToCollectionIfMissing<IDescontoPlanoContabil>(
        this.descontoPlanoContabilsSharedCollection,
        calculoPlanoAssinatura.descontoPlanoContabil,
      );
    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      calculoPlanoAssinatura.assinaturaEmpresa,
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
            this.calculoPlanoAssinatura?.periodoPagamento,
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
            this.calculoPlanoAssinatura?.planoContabil,
          ),
        ),
      )
      .subscribe((planoContabils: IPlanoContabil[]) => (this.planoContabilsSharedCollection = planoContabils));

    this.ramoService
      .query()
      .pipe(map((res: HttpResponse<IRamo[]>) => res.body ?? []))
      .pipe(map((ramos: IRamo[]) => this.ramoService.addRamoToCollectionIfMissing<IRamo>(ramos, this.calculoPlanoAssinatura?.ramo)))
      .subscribe((ramos: IRamo[]) => (this.ramosSharedCollection = ramos));

    this.tributacaoService
      .query()
      .pipe(map((res: HttpResponse<ITributacao[]>) => res.body ?? []))
      .pipe(
        map((tributacaos: ITributacao[]) =>
          this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(tributacaos, this.calculoPlanoAssinatura?.tributacao),
        ),
      )
      .subscribe((tributacaos: ITributacao[]) => (this.tributacaosSharedCollection = tributacaos));

    this.descontoPlanoContabilService
      .query()
      .pipe(map((res: HttpResponse<IDescontoPlanoContabil[]>) => res.body ?? []))
      .pipe(
        map((descontoPlanoContabils: IDescontoPlanoContabil[]) =>
          this.descontoPlanoContabilService.addDescontoPlanoContabilToCollectionIfMissing<IDescontoPlanoContabil>(
            descontoPlanoContabils,
            this.calculoPlanoAssinatura?.descontoPlanoContabil,
          ),
        ),
      )
      .subscribe(
        (descontoPlanoContabils: IDescontoPlanoContabil[]) => (this.descontoPlanoContabilsSharedCollection = descontoPlanoContabils),
      );

    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.calculoPlanoAssinatura?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));
  }
}
