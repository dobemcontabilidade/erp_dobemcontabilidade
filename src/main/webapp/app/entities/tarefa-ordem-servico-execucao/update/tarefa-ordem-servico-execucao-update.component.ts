import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaOrdemServico } from 'app/entities/tarefa-ordem-servico/tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from 'app/entities/tarefa-ordem-servico/service/tarefa-ordem-servico.service';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';
import { SituacaoTarefaEnum } from 'app/entities/enumerations/situacao-tarefa-enum.model';
import { TarefaOrdemServicoExecucaoService } from '../service/tarefa-ordem-servico-execucao.service';
import { ITarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoFormService, TarefaOrdemServicoExecucaoFormGroup } from './tarefa-ordem-servico-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-ordem-servico-execucao-update',
  templateUrl: './tarefa-ordem-servico-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaOrdemServicoExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao | null = null;
  mesCompetenciaEnumValues = Object.keys(MesCompetenciaEnum);
  situacaoTarefaEnumValues = Object.keys(SituacaoTarefaEnum);

  tarefaOrdemServicosSharedCollection: ITarefaOrdemServico[] = [];

  protected tarefaOrdemServicoExecucaoService = inject(TarefaOrdemServicoExecucaoService);
  protected tarefaOrdemServicoExecucaoFormService = inject(TarefaOrdemServicoExecucaoFormService);
  protected tarefaOrdemServicoService = inject(TarefaOrdemServicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaOrdemServicoExecucaoFormGroup = this.tarefaOrdemServicoExecucaoFormService.createTarefaOrdemServicoExecucaoFormGroup();

  compareTarefaOrdemServico = (o1: ITarefaOrdemServico | null, o2: ITarefaOrdemServico | null): boolean =>
    this.tarefaOrdemServicoService.compareTarefaOrdemServico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefaOrdemServicoExecucao }) => {
      this.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;
      if (tarefaOrdemServicoExecucao) {
        this.updateForm(tarefaOrdemServicoExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefaOrdemServicoExecucao = this.tarefaOrdemServicoExecucaoFormService.getTarefaOrdemServicoExecucao(this.editForm);
    if (tarefaOrdemServicoExecucao.id !== null) {
      this.subscribeToSaveResponse(this.tarefaOrdemServicoExecucaoService.update(tarefaOrdemServicoExecucao));
    } else {
      this.subscribeToSaveResponse(this.tarefaOrdemServicoExecucaoService.create(tarefaOrdemServicoExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefaOrdemServicoExecucao>>): void {
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

  protected updateForm(tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao): void {
    this.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;
    this.tarefaOrdemServicoExecucaoFormService.resetForm(this.editForm, tarefaOrdemServicoExecucao);

    this.tarefaOrdemServicosSharedCollection =
      this.tarefaOrdemServicoService.addTarefaOrdemServicoToCollectionIfMissing<ITarefaOrdemServico>(
        this.tarefaOrdemServicosSharedCollection,
        tarefaOrdemServicoExecucao.tarefaOrdemServico,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.tarefaOrdemServicoService
      .query()
      .pipe(map((res: HttpResponse<ITarefaOrdemServico[]>) => res.body ?? []))
      .pipe(
        map((tarefaOrdemServicos: ITarefaOrdemServico[]) =>
          this.tarefaOrdemServicoService.addTarefaOrdemServicoToCollectionIfMissing<ITarefaOrdemServico>(
            tarefaOrdemServicos,
            this.tarefaOrdemServicoExecucao?.tarefaOrdemServico,
          ),
        ),
      )
      .subscribe((tarefaOrdemServicos: ITarefaOrdemServico[]) => (this.tarefaOrdemServicosSharedCollection = tarefaOrdemServicos));
  }
}
