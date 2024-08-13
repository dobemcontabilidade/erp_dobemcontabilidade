import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaRecorrente } from 'app/entities/tarefa-recorrente/tarefa-recorrente.model';
import { TarefaRecorrenteService } from 'app/entities/tarefa-recorrente/service/tarefa-recorrente.service';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';
import { SituacaoTarefaEnum } from 'app/entities/enumerations/situacao-tarefa-enum.model';
import { TarefaRecorrenteExecucaoService } from '../service/tarefa-recorrente-execucao.service';
import { ITarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoFormService, TarefaRecorrenteExecucaoFormGroup } from './tarefa-recorrente-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-recorrente-execucao-update',
  templateUrl: './tarefa-recorrente-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaRecorrenteExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao | null = null;
  mesCompetenciaEnumValues = Object.keys(MesCompetenciaEnum);
  situacaoTarefaEnumValues = Object.keys(SituacaoTarefaEnum);

  tarefaRecorrentesSharedCollection: ITarefaRecorrente[] = [];

  protected tarefaRecorrenteExecucaoService = inject(TarefaRecorrenteExecucaoService);
  protected tarefaRecorrenteExecucaoFormService = inject(TarefaRecorrenteExecucaoFormService);
  protected tarefaRecorrenteService = inject(TarefaRecorrenteService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaRecorrenteExecucaoFormGroup = this.tarefaRecorrenteExecucaoFormService.createTarefaRecorrenteExecucaoFormGroup();

  compareTarefaRecorrente = (o1: ITarefaRecorrente | null, o2: ITarefaRecorrente | null): boolean =>
    this.tarefaRecorrenteService.compareTarefaRecorrente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefaRecorrenteExecucao }) => {
      this.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;
      if (tarefaRecorrenteExecucao) {
        this.updateForm(tarefaRecorrenteExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefaRecorrenteExecucao = this.tarefaRecorrenteExecucaoFormService.getTarefaRecorrenteExecucao(this.editForm);
    if (tarefaRecorrenteExecucao.id !== null) {
      this.subscribeToSaveResponse(this.tarefaRecorrenteExecucaoService.update(tarefaRecorrenteExecucao));
    } else {
      this.subscribeToSaveResponse(this.tarefaRecorrenteExecucaoService.create(tarefaRecorrenteExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefaRecorrenteExecucao>>): void {
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

  protected updateForm(tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao): void {
    this.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;
    this.tarefaRecorrenteExecucaoFormService.resetForm(this.editForm, tarefaRecorrenteExecucao);

    this.tarefaRecorrentesSharedCollection = this.tarefaRecorrenteService.addTarefaRecorrenteToCollectionIfMissing<ITarefaRecorrente>(
      this.tarefaRecorrentesSharedCollection,
      tarefaRecorrenteExecucao.tarefaRecorrente,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tarefaRecorrenteService
      .query()
      .pipe(map((res: HttpResponse<ITarefaRecorrente[]>) => res.body ?? []))
      .pipe(
        map((tarefaRecorrentes: ITarefaRecorrente[]) =>
          this.tarefaRecorrenteService.addTarefaRecorrenteToCollectionIfMissing<ITarefaRecorrente>(
            tarefaRecorrentes,
            this.tarefaRecorrenteExecucao?.tarefaRecorrente,
          ),
        ),
      )
      .subscribe((tarefaRecorrentes: ITarefaRecorrente[]) => (this.tarefaRecorrentesSharedCollection = tarefaRecorrentes));
  }
}
