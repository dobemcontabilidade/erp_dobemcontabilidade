import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { ISubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';
import { SubTarefaOrdemServicoService } from '../service/sub-tarefa-ordem-servico.service';
import { SubTarefaOrdemServicoFormService, SubTarefaOrdemServicoFormGroup } from './sub-tarefa-ordem-servico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sub-tarefa-ordem-servico-update',
  templateUrl: './sub-tarefa-ordem-servico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SubTarefaOrdemServicoUpdateComponent implements OnInit {
  isSaving = false;
  subTarefaOrdemServico: ISubTarefaOrdemServico | null = null;

  tarefaOrdemServicoExecucaosSharedCollection: ITarefaOrdemServicoExecucao[] = [];

  protected subTarefaOrdemServicoService = inject(SubTarefaOrdemServicoService);
  protected subTarefaOrdemServicoFormService = inject(SubTarefaOrdemServicoFormService);
  protected tarefaOrdemServicoExecucaoService = inject(TarefaOrdemServicoExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SubTarefaOrdemServicoFormGroup = this.subTarefaOrdemServicoFormService.createSubTarefaOrdemServicoFormGroup();

  compareTarefaOrdemServicoExecucao = (o1: ITarefaOrdemServicoExecucao | null, o2: ITarefaOrdemServicoExecucao | null): boolean =>
    this.tarefaOrdemServicoExecucaoService.compareTarefaOrdemServicoExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subTarefaOrdemServico }) => {
      this.subTarefaOrdemServico = subTarefaOrdemServico;
      if (subTarefaOrdemServico) {
        this.updateForm(subTarefaOrdemServico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subTarefaOrdemServico = this.subTarefaOrdemServicoFormService.getSubTarefaOrdemServico(this.editForm);
    if (subTarefaOrdemServico.id !== null) {
      this.subscribeToSaveResponse(this.subTarefaOrdemServicoService.update(subTarefaOrdemServico));
    } else {
      this.subscribeToSaveResponse(this.subTarefaOrdemServicoService.create(subTarefaOrdemServico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubTarefaOrdemServico>>): void {
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

  protected updateForm(subTarefaOrdemServico: ISubTarefaOrdemServico): void {
    this.subTarefaOrdemServico = subTarefaOrdemServico;
    this.subTarefaOrdemServicoFormService.resetForm(this.editForm, subTarefaOrdemServico);

    this.tarefaOrdemServicoExecucaosSharedCollection =
      this.tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing<ITarefaOrdemServicoExecucao>(
        this.tarefaOrdemServicoExecucaosSharedCollection,
        subTarefaOrdemServico.tarefaOrdemServicoExecucao,
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
            this.subTarefaOrdemServico?.tarefaOrdemServicoExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaOrdemServicoExecucaos: ITarefaOrdemServicoExecucao[]) =>
          (this.tarefaOrdemServicoExecucaosSharedCollection = tarefaOrdemServicoExecucaos),
      );
  }
}
