import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { IAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';
import { AnexoOrdemServicoExecucaoService } from '../service/anexo-ordem-servico-execucao.service';
import { AnexoOrdemServicoExecucaoFormService, AnexoOrdemServicoExecucaoFormGroup } from './anexo-ordem-servico-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-ordem-servico-execucao-update',
  templateUrl: './anexo-ordem-servico-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoOrdemServicoExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao | null = null;

  tarefaOrdemServicoExecucaosSharedCollection: ITarefaOrdemServicoExecucao[] = [];

  protected anexoOrdemServicoExecucaoService = inject(AnexoOrdemServicoExecucaoService);
  protected anexoOrdemServicoExecucaoFormService = inject(AnexoOrdemServicoExecucaoFormService);
  protected tarefaOrdemServicoExecucaoService = inject(TarefaOrdemServicoExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoOrdemServicoExecucaoFormGroup = this.anexoOrdemServicoExecucaoFormService.createAnexoOrdemServicoExecucaoFormGroup();

  compareTarefaOrdemServicoExecucao = (o1: ITarefaOrdemServicoExecucao | null, o2: ITarefaOrdemServicoExecucao | null): boolean =>
    this.tarefaOrdemServicoExecucaoService.compareTarefaOrdemServicoExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoOrdemServicoExecucao }) => {
      this.anexoOrdemServicoExecucao = anexoOrdemServicoExecucao;
      if (anexoOrdemServicoExecucao) {
        this.updateForm(anexoOrdemServicoExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexoOrdemServicoExecucao = this.anexoOrdemServicoExecucaoFormService.getAnexoOrdemServicoExecucao(this.editForm);
    if (anexoOrdemServicoExecucao.id !== null) {
      this.subscribeToSaveResponse(this.anexoOrdemServicoExecucaoService.update(anexoOrdemServicoExecucao));
    } else {
      this.subscribeToSaveResponse(this.anexoOrdemServicoExecucaoService.create(anexoOrdemServicoExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoOrdemServicoExecucao>>): void {
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

  protected updateForm(anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao): void {
    this.anexoOrdemServicoExecucao = anexoOrdemServicoExecucao;
    this.anexoOrdemServicoExecucaoFormService.resetForm(this.editForm, anexoOrdemServicoExecucao);

    this.tarefaOrdemServicoExecucaosSharedCollection =
      this.tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing<ITarefaOrdemServicoExecucao>(
        this.tarefaOrdemServicoExecucaosSharedCollection,
        anexoOrdemServicoExecucao.tarefaOrdemServicoExecucao,
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
            this.anexoOrdemServicoExecucao?.tarefaOrdemServicoExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaOrdemServicoExecucaos: ITarefaOrdemServicoExecucao[]) =>
          (this.tarefaOrdemServicoExecucaosSharedCollection = tarefaOrdemServicoExecucaos),
      );
  }
}
