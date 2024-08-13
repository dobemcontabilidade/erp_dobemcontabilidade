import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';
import { EtapaFluxoExecucaoService } from '../service/etapa-fluxo-execucao.service';
import { EtapaFluxoExecucaoFormService, EtapaFluxoExecucaoFormGroup } from './etapa-fluxo-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-etapa-fluxo-execucao-update',
  templateUrl: './etapa-fluxo-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EtapaFluxoExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  etapaFluxoExecucao: IEtapaFluxoExecucao | null = null;

  ordemServicosSharedCollection: IOrdemServico[] = [];

  protected etapaFluxoExecucaoService = inject(EtapaFluxoExecucaoService);
  protected etapaFluxoExecucaoFormService = inject(EtapaFluxoExecucaoFormService);
  protected ordemServicoService = inject(OrdemServicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EtapaFluxoExecucaoFormGroup = this.etapaFluxoExecucaoFormService.createEtapaFluxoExecucaoFormGroup();

  compareOrdemServico = (o1: IOrdemServico | null, o2: IOrdemServico | null): boolean =>
    this.ordemServicoService.compareOrdemServico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etapaFluxoExecucao }) => {
      this.etapaFluxoExecucao = etapaFluxoExecucao;
      if (etapaFluxoExecucao) {
        this.updateForm(etapaFluxoExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etapaFluxoExecucao = this.etapaFluxoExecucaoFormService.getEtapaFluxoExecucao(this.editForm);
    if (etapaFluxoExecucao.id !== null) {
      this.subscribeToSaveResponse(this.etapaFluxoExecucaoService.update(etapaFluxoExecucao));
    } else {
      this.subscribeToSaveResponse(this.etapaFluxoExecucaoService.create(etapaFluxoExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtapaFluxoExecucao>>): void {
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

  protected updateForm(etapaFluxoExecucao: IEtapaFluxoExecucao): void {
    this.etapaFluxoExecucao = etapaFluxoExecucao;
    this.etapaFluxoExecucaoFormService.resetForm(this.editForm, etapaFluxoExecucao);

    this.ordemServicosSharedCollection = this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
      this.ordemServicosSharedCollection,
      etapaFluxoExecucao.ordemServico,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ordemServicoService
      .query()
      .pipe(map((res: HttpResponse<IOrdemServico[]>) => res.body ?? []))
      .pipe(
        map((ordemServicos: IOrdemServico[]) =>
          this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
            ordemServicos,
            this.etapaFluxoExecucao?.ordemServico,
          ),
        ),
      )
      .subscribe((ordemServicos: IOrdemServico[]) => (this.ordemServicosSharedCollection = ordemServicos));
  }
}
