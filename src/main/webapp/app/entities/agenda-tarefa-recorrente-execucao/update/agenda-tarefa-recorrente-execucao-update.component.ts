import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { IAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';
import { AgendaTarefaRecorrenteExecucaoService } from '../service/agenda-tarefa-recorrente-execucao.service';
import {
  AgendaTarefaRecorrenteExecucaoFormService,
  AgendaTarefaRecorrenteExecucaoFormGroup,
} from './agenda-tarefa-recorrente-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agenda-tarefa-recorrente-execucao-update',
  templateUrl: './agenda-tarefa-recorrente-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgendaTarefaRecorrenteExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao | null = null;

  tarefaRecorrenteExecucaosSharedCollection: ITarefaRecorrenteExecucao[] = [];

  protected agendaTarefaRecorrenteExecucaoService = inject(AgendaTarefaRecorrenteExecucaoService);
  protected agendaTarefaRecorrenteExecucaoFormService = inject(AgendaTarefaRecorrenteExecucaoFormService);
  protected tarefaRecorrenteExecucaoService = inject(TarefaRecorrenteExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgendaTarefaRecorrenteExecucaoFormGroup =
    this.agendaTarefaRecorrenteExecucaoFormService.createAgendaTarefaRecorrenteExecucaoFormGroup();

  compareTarefaRecorrenteExecucao = (o1: ITarefaRecorrenteExecucao | null, o2: ITarefaRecorrenteExecucao | null): boolean =>
    this.tarefaRecorrenteExecucaoService.compareTarefaRecorrenteExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agendaTarefaRecorrenteExecucao }) => {
      this.agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucao;
      if (agendaTarefaRecorrenteExecucao) {
        this.updateForm(agendaTarefaRecorrenteExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agendaTarefaRecorrenteExecucao = this.agendaTarefaRecorrenteExecucaoFormService.getAgendaTarefaRecorrenteExecucao(this.editForm);
    if (agendaTarefaRecorrenteExecucao.id !== null) {
      this.subscribeToSaveResponse(this.agendaTarefaRecorrenteExecucaoService.update(agendaTarefaRecorrenteExecucao));
    } else {
      this.subscribeToSaveResponse(this.agendaTarefaRecorrenteExecucaoService.create(agendaTarefaRecorrenteExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaTarefaRecorrenteExecucao>>): void {
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

  protected updateForm(agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao): void {
    this.agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucao;
    this.agendaTarefaRecorrenteExecucaoFormService.resetForm(this.editForm, agendaTarefaRecorrenteExecucao);

    this.tarefaRecorrenteExecucaosSharedCollection =
      this.tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing<ITarefaRecorrenteExecucao>(
        this.tarefaRecorrenteExecucaosSharedCollection,
        agendaTarefaRecorrenteExecucao.tarefaRecorrenteExecucao,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.tarefaRecorrenteExecucaoService
      .query()
      .pipe(map((res: HttpResponse<ITarefaRecorrenteExecucao[]>) => res.body ?? []))
      .pipe(
        map((tarefaRecorrenteExecucaos: ITarefaRecorrenteExecucao[]) =>
          this.tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing<ITarefaRecorrenteExecucao>(
            tarefaRecorrenteExecucaos,
            this.agendaTarefaRecorrenteExecucao?.tarefaRecorrenteExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaRecorrenteExecucaos: ITarefaRecorrenteExecucao[]) =>
          (this.tarefaRecorrenteExecucaosSharedCollection = tarefaRecorrenteExecucaos),
      );
  }
}
