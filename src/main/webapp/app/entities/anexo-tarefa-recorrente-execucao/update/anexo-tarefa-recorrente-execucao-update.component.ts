import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { IAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';
import { AnexoTarefaRecorrenteExecucaoService } from '../service/anexo-tarefa-recorrente-execucao.service';
import {
  AnexoTarefaRecorrenteExecucaoFormService,
  AnexoTarefaRecorrenteExecucaoFormGroup,
} from './anexo-tarefa-recorrente-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-tarefa-recorrente-execucao-update',
  templateUrl: './anexo-tarefa-recorrente-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoTarefaRecorrenteExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao | null = null;

  tarefaRecorrenteExecucaosSharedCollection: ITarefaRecorrenteExecucao[] = [];

  protected anexoTarefaRecorrenteExecucaoService = inject(AnexoTarefaRecorrenteExecucaoService);
  protected anexoTarefaRecorrenteExecucaoFormService = inject(AnexoTarefaRecorrenteExecucaoFormService);
  protected tarefaRecorrenteExecucaoService = inject(TarefaRecorrenteExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoTarefaRecorrenteExecucaoFormGroup =
    this.anexoTarefaRecorrenteExecucaoFormService.createAnexoTarefaRecorrenteExecucaoFormGroup();

  compareTarefaRecorrenteExecucao = (o1: ITarefaRecorrenteExecucao | null, o2: ITarefaRecorrenteExecucao | null): boolean =>
    this.tarefaRecorrenteExecucaoService.compareTarefaRecorrenteExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoTarefaRecorrenteExecucao }) => {
      this.anexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucao;
      if (anexoTarefaRecorrenteExecucao) {
        this.updateForm(anexoTarefaRecorrenteExecucao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexoTarefaRecorrenteExecucao = this.anexoTarefaRecorrenteExecucaoFormService.getAnexoTarefaRecorrenteExecucao(this.editForm);
    if (anexoTarefaRecorrenteExecucao.id !== null) {
      this.subscribeToSaveResponse(this.anexoTarefaRecorrenteExecucaoService.update(anexoTarefaRecorrenteExecucao));
    } else {
      this.subscribeToSaveResponse(this.anexoTarefaRecorrenteExecucaoService.create(anexoTarefaRecorrenteExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoTarefaRecorrenteExecucao>>): void {
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

  protected updateForm(anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao): void {
    this.anexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucao;
    this.anexoTarefaRecorrenteExecucaoFormService.resetForm(this.editForm, anexoTarefaRecorrenteExecucao);

    this.tarefaRecorrenteExecucaosSharedCollection =
      this.tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing<ITarefaRecorrenteExecucao>(
        this.tarefaRecorrenteExecucaosSharedCollection,
        anexoTarefaRecorrenteExecucao.tarefaRecorrenteExecucao,
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
            this.anexoTarefaRecorrenteExecucao?.tarefaRecorrenteExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaRecorrenteExecucaos: ITarefaRecorrenteExecucao[]) =>
          (this.tarefaRecorrenteExecucaosSharedCollection = tarefaRecorrenteExecucaos),
      );
  }
}
