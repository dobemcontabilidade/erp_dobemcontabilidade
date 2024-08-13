import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { IAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';
import { AgendaTarefaOrdemServicoExecucaoService } from '../service/agenda-tarefa-ordem-servico-execucao.service';
import {
  AgendaTarefaOrdemServicoExecucaoFormService,
  AgendaTarefaOrdemServicoExecucaoFormGroup,
} from './agenda-tarefa-ordem-servico-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agenda-tarefa-ordem-servico-execucao-update',
  templateUrl: './agenda-tarefa-ordem-servico-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgendaTarefaOrdemServicoExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao | null = null;

  tarefaOrdemServicoExecucaosSharedCollection: ITarefaOrdemServicoExecucao[] = [];

  protected agendaTarefaOrdemServicoExecucaoService = inject(AgendaTarefaOrdemServicoExecucaoService);
  protected agendaTarefaOrdemServicoExecucaoFormService = inject(AgendaTarefaOrdemServicoExecucaoFormService);
  protected tarefaOrdemServicoExecucaoService = inject(TarefaOrdemServicoExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgendaTarefaOrdemServicoExecucaoFormGroup =
    this.agendaTarefaOrdemServicoExecucaoFormService.createAgendaTarefaOrdemServicoExecucaoFormGroup();

  compareTarefaOrdemServicoExecucao = (o1: ITarefaOrdemServicoExecucao | null, o2: ITarefaOrdemServicoExecucao | null): boolean =>
    this.tarefaOrdemServicoExecucaoService.compareTarefaOrdemServicoExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agendaTarefaOrdemServicoExecucao }) => {
      this.agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucao;
      if (agendaTarefaOrdemServicoExecucao) {
        this.updateForm(agendaTarefaOrdemServicoExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agendaTarefaOrdemServicoExecucao = this.agendaTarefaOrdemServicoExecucaoFormService.getAgendaTarefaOrdemServicoExecucao(
      this.editForm,
    );
    if (agendaTarefaOrdemServicoExecucao.id !== null) {
      this.subscribeToSaveResponse(this.agendaTarefaOrdemServicoExecucaoService.update(agendaTarefaOrdemServicoExecucao));
    } else {
      this.subscribeToSaveResponse(this.agendaTarefaOrdemServicoExecucaoService.create(agendaTarefaOrdemServicoExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaTarefaOrdemServicoExecucao>>): void {
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

  protected updateForm(agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao): void {
    this.agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucao;
    this.agendaTarefaOrdemServicoExecucaoFormService.resetForm(this.editForm, agendaTarefaOrdemServicoExecucao);

    this.tarefaOrdemServicoExecucaosSharedCollection =
      this.tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing<ITarefaOrdemServicoExecucao>(
        this.tarefaOrdemServicoExecucaosSharedCollection,
        agendaTarefaOrdemServicoExecucao.tarefaOrdemServicoExecucao,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.tarefaOrdemServicoExecucaoService
      .query()
      .pipe(map((res: HttpResponse<ITarefaOrdemServicoExecucao[]>) => res.body ?? []))
      .pipe(
        map((tarefaOrdemServicoExecucaos: ITarefaOrdemServicoExecucao[]) =>
          this.tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing<ITarefaOrdemServicoExecucao>(
            tarefaOrdemServicoExecucaos,
            this.agendaTarefaOrdemServicoExecucao?.tarefaOrdemServicoExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaOrdemServicoExecucaos: ITarefaOrdemServicoExecucao[]) =>
          (this.tarefaOrdemServicoExecucaosSharedCollection = tarefaOrdemServicoExecucaos),
      );
  }
}
