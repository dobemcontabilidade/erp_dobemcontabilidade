import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAgendaTarefaRecorrenteExecucao } from 'app/entities/agenda-tarefa-recorrente-execucao/agenda-tarefa-recorrente-execucao.model';
import { AgendaTarefaRecorrenteExecucaoService } from 'app/entities/agenda-tarefa-recorrente-execucao/service/agenda-tarefa-recorrente-execucao.service';
import { IAgendaTarefaOrdemServicoExecucao } from 'app/entities/agenda-tarefa-ordem-servico-execucao/agenda-tarefa-ordem-servico-execucao.model';
import { AgendaTarefaOrdemServicoExecucaoService } from 'app/entities/agenda-tarefa-ordem-servico-execucao/service/agenda-tarefa-ordem-servico-execucao.service';
import { TipoVisualizacaoAgendaEnum } from 'app/entities/enumerations/tipo-visualizacao-agenda-enum.model';
import { AgendaContadorConfigService } from '../service/agenda-contador-config.service';
import { IAgendaContadorConfig } from '../agenda-contador-config.model';
import { AgendaContadorConfigFormService, AgendaContadorConfigFormGroup } from './agenda-contador-config-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agenda-contador-config-update',
  templateUrl: './agenda-contador-config-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgendaContadorConfigUpdateComponent implements OnInit {
  isSaving = false;
  agendaContadorConfig: IAgendaContadorConfig | null = null;
  tipoVisualizacaoAgendaEnumValues = Object.keys(TipoVisualizacaoAgendaEnum);

  agendaTarefaRecorrenteExecucaosSharedCollection: IAgendaTarefaRecorrenteExecucao[] = [];
  agendaTarefaOrdemServicoExecucaosSharedCollection: IAgendaTarefaOrdemServicoExecucao[] = [];

  protected agendaContadorConfigService = inject(AgendaContadorConfigService);
  protected agendaContadorConfigFormService = inject(AgendaContadorConfigFormService);
  protected agendaTarefaRecorrenteExecucaoService = inject(AgendaTarefaRecorrenteExecucaoService);
  protected agendaTarefaOrdemServicoExecucaoService = inject(AgendaTarefaOrdemServicoExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgendaContadorConfigFormGroup = this.agendaContadorConfigFormService.createAgendaContadorConfigFormGroup();

  compareAgendaTarefaRecorrenteExecucao = (
    o1: IAgendaTarefaRecorrenteExecucao | null,
    o2: IAgendaTarefaRecorrenteExecucao | null,
  ): boolean => this.agendaTarefaRecorrenteExecucaoService.compareAgendaTarefaRecorrenteExecucao(o1, o2);

  compareAgendaTarefaOrdemServicoExecucao = (
    o1: IAgendaTarefaOrdemServicoExecucao | null,
    o2: IAgendaTarefaOrdemServicoExecucao | null,
  ): boolean => this.agendaTarefaOrdemServicoExecucaoService.compareAgendaTarefaOrdemServicoExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agendaContadorConfig }) => {
      this.agendaContadorConfig = agendaContadorConfig;
      if (agendaContadorConfig) {
        this.updateForm(agendaContadorConfig);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agendaContadorConfig = this.agendaContadorConfigFormService.getAgendaContadorConfig(this.editForm);
    if (agendaContadorConfig.id !== null) {
      this.subscribeToSaveResponse(this.agendaContadorConfigService.update(agendaContadorConfig));
    } else {
      this.subscribeToSaveResponse(this.agendaContadorConfigService.create(agendaContadorConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaContadorConfig>>): void {
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

  protected updateForm(agendaContadorConfig: IAgendaContadorConfig): void {
    this.agendaContadorConfig = agendaContadorConfig;
    this.agendaContadorConfigFormService.resetForm(this.editForm, agendaContadorConfig);

    this.agendaTarefaRecorrenteExecucaosSharedCollection =
      this.agendaTarefaRecorrenteExecucaoService.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing<IAgendaTarefaRecorrenteExecucao>(
        this.agendaTarefaRecorrenteExecucaosSharedCollection,
        agendaContadorConfig.agendaTarefaRecorrenteExecucao,
      );
    this.agendaTarefaOrdemServicoExecucaosSharedCollection =
      this.agendaTarefaOrdemServicoExecucaoService.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing<IAgendaTarefaOrdemServicoExecucao>(
        this.agendaTarefaOrdemServicoExecucaosSharedCollection,
        agendaContadorConfig.agendaTarefaOrdemServicoExecucao,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.agendaTarefaRecorrenteExecucaoService
      .query()
      .pipe(map((res: HttpResponse<IAgendaTarefaRecorrenteExecucao[]>) => res.body ?? []))
      .pipe(
        map((agendaTarefaRecorrenteExecucaos: IAgendaTarefaRecorrenteExecucao[]) =>
          this.agendaTarefaRecorrenteExecucaoService.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing<IAgendaTarefaRecorrenteExecucao>(
            agendaTarefaRecorrenteExecucaos,
            this.agendaContadorConfig?.agendaTarefaRecorrenteExecucao,
          ),
        ),
      )
      .subscribe(
        (agendaTarefaRecorrenteExecucaos: IAgendaTarefaRecorrenteExecucao[]) =>
          (this.agendaTarefaRecorrenteExecucaosSharedCollection = agendaTarefaRecorrenteExecucaos),
      );

    this.agendaTarefaOrdemServicoExecucaoService
      .query()
      .pipe(map((res: HttpResponse<IAgendaTarefaOrdemServicoExecucao[]>) => res.body ?? []))
      .pipe(
        map((agendaTarefaOrdemServicoExecucaos: IAgendaTarefaOrdemServicoExecucao[]) =>
          this.agendaTarefaOrdemServicoExecucaoService.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing<IAgendaTarefaOrdemServicoExecucao>(
            agendaTarefaOrdemServicoExecucaos,
            this.agendaContadorConfig?.agendaTarefaOrdemServicoExecucao,
          ),
        ),
      )
      .subscribe(
        (agendaTarefaOrdemServicoExecucaos: IAgendaTarefaOrdemServicoExecucao[]) =>
          (this.agendaTarefaOrdemServicoExecucaosSharedCollection = agendaTarefaOrdemServicoExecucaos),
      );
  }
}
