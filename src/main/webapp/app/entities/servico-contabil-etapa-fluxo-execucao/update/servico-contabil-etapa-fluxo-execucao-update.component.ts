import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IEtapaFluxoExecucao } from 'app/entities/etapa-fluxo-execucao/etapa-fluxo-execucao.model';
import { EtapaFluxoExecucaoService } from 'app/entities/etapa-fluxo-execucao/service/etapa-fluxo-execucao.service';
import { ServicoContabilEtapaFluxoExecucaoService } from '../service/servico-contabil-etapa-fluxo-execucao.service';
import { IServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';
import {
  ServicoContabilEtapaFluxoExecucaoFormService,
  ServicoContabilEtapaFluxoExecucaoFormGroup,
} from './servico-contabil-etapa-fluxo-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-etapa-fluxo-execucao-update',
  templateUrl: './servico-contabil-etapa-fluxo-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServicoContabilEtapaFluxoExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao | null = null;

  servicoContabilsSharedCollection: IServicoContabil[] = [];
  etapaFluxoExecucaosSharedCollection: IEtapaFluxoExecucao[] = [];

  protected servicoContabilEtapaFluxoExecucaoService = inject(ServicoContabilEtapaFluxoExecucaoService);
  protected servicoContabilEtapaFluxoExecucaoFormService = inject(ServicoContabilEtapaFluxoExecucaoFormService);
  protected servicoContabilService = inject(ServicoContabilService);
  protected etapaFluxoExecucaoService = inject(EtapaFluxoExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServicoContabilEtapaFluxoExecucaoFormGroup =
    this.servicoContabilEtapaFluxoExecucaoFormService.createServicoContabilEtapaFluxoExecucaoFormGroup();

  compareServicoContabil = (o1: IServicoContabil | null, o2: IServicoContabil | null): boolean =>
    this.servicoContabilService.compareServicoContabil(o1, o2);

  compareEtapaFluxoExecucao = (o1: IEtapaFluxoExecucao | null, o2: IEtapaFluxoExecucao | null): boolean =>
    this.etapaFluxoExecucaoService.compareEtapaFluxoExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicoContabilEtapaFluxoExecucao }) => {
      this.servicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucao;
      if (servicoContabilEtapaFluxoExecucao) {
        this.updateForm(servicoContabilEtapaFluxoExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicoContabilEtapaFluxoExecucao = this.servicoContabilEtapaFluxoExecucaoFormService.getServicoContabilEtapaFluxoExecucao(
      this.editForm,
    );
    if (servicoContabilEtapaFluxoExecucao.id !== null) {
      this.subscribeToSaveResponse(this.servicoContabilEtapaFluxoExecucaoService.update(servicoContabilEtapaFluxoExecucao));
    } else {
      this.subscribeToSaveResponse(this.servicoContabilEtapaFluxoExecucaoService.create(servicoContabilEtapaFluxoExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicoContabilEtapaFluxoExecucao>>): void {
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

  protected updateForm(servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao): void {
    this.servicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucao;
    this.servicoContabilEtapaFluxoExecucaoFormService.resetForm(this.editForm, servicoContabilEtapaFluxoExecucao);

    this.servicoContabilsSharedCollection = this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
      this.servicoContabilsSharedCollection,
      servicoContabilEtapaFluxoExecucao.servicoContabil,
    );
    this.etapaFluxoExecucaosSharedCollection =
      this.etapaFluxoExecucaoService.addEtapaFluxoExecucaoToCollectionIfMissing<IEtapaFluxoExecucao>(
        this.etapaFluxoExecucaosSharedCollection,
        servicoContabilEtapaFluxoExecucao.etapaFluxoExecucao,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.servicoContabilService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabil[]>) => res.body ?? []))
      .pipe(
        map((servicoContabils: IServicoContabil[]) =>
          this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
            servicoContabils,
            this.servicoContabilEtapaFluxoExecucao?.servicoContabil,
          ),
        ),
      )
      .subscribe((servicoContabils: IServicoContabil[]) => (this.servicoContabilsSharedCollection = servicoContabils));

    this.etapaFluxoExecucaoService
      .query()
      .pipe(map((res: HttpResponse<IEtapaFluxoExecucao[]>) => res.body ?? []))
      .pipe(
        map((etapaFluxoExecucaos: IEtapaFluxoExecucao[]) =>
          this.etapaFluxoExecucaoService.addEtapaFluxoExecucaoToCollectionIfMissing<IEtapaFluxoExecucao>(
            etapaFluxoExecucaos,
            this.servicoContabilEtapaFluxoExecucao?.etapaFluxoExecucao,
          ),
        ),
      )
      .subscribe((etapaFluxoExecucaos: IEtapaFluxoExecucao[]) => (this.etapaFluxoExecucaosSharedCollection = etapaFluxoExecucaos));
  }
}
