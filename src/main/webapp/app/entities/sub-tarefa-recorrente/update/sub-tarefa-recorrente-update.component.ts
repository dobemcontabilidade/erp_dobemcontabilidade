import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { ISubTarefaRecorrente } from '../sub-tarefa-recorrente.model';
import { SubTarefaRecorrenteService } from '../service/sub-tarefa-recorrente.service';
import { SubTarefaRecorrenteFormService, SubTarefaRecorrenteFormGroup } from './sub-tarefa-recorrente-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sub-tarefa-recorrente-update',
  templateUrl: './sub-tarefa-recorrente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SubTarefaRecorrenteUpdateComponent implements OnInit {
  isSaving = false;
  subTarefaRecorrente: ISubTarefaRecorrente | null = null;

  tarefaRecorrenteExecucaosSharedCollection: ITarefaRecorrenteExecucao[] = [];

  protected subTarefaRecorrenteService = inject(SubTarefaRecorrenteService);
  protected subTarefaRecorrenteFormService = inject(SubTarefaRecorrenteFormService);
  protected tarefaRecorrenteExecucaoService = inject(TarefaRecorrenteExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SubTarefaRecorrenteFormGroup = this.subTarefaRecorrenteFormService.createSubTarefaRecorrenteFormGroup();

  compareTarefaRecorrenteExecucao = (o1: ITarefaRecorrenteExecucao | null, o2: ITarefaRecorrenteExecucao | null): boolean =>
    this.tarefaRecorrenteExecucaoService.compareTarefaRecorrenteExecucao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subTarefaRecorrente }) => {
      this.subTarefaRecorrente = subTarefaRecorrente;
      if (subTarefaRecorrente) {
        this.updateForm(subTarefaRecorrente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subTarefaRecorrente = this.subTarefaRecorrenteFormService.getSubTarefaRecorrente(this.editForm);
    if (subTarefaRecorrente.id !== null) {
      this.subscribeToSaveResponse(this.subTarefaRecorrenteService.update(subTarefaRecorrente));
    } else {
      this.subscribeToSaveResponse(this.subTarefaRecorrenteService.create(subTarefaRecorrente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubTarefaRecorrente>>): void {
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

  protected updateForm(subTarefaRecorrente: ISubTarefaRecorrente): void {
    this.subTarefaRecorrente = subTarefaRecorrente;
    this.subTarefaRecorrenteFormService.resetForm(this.editForm, subTarefaRecorrente);

    this.tarefaRecorrenteExecucaosSharedCollection =
      this.tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing<ITarefaRecorrenteExecucao>(
        this.tarefaRecorrenteExecucaosSharedCollection,
        subTarefaRecorrente.tarefaRecorrenteExecucao,
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
            this.subTarefaRecorrente?.tarefaRecorrenteExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaRecorrenteExecucaos: ITarefaRecorrenteExecucao[]) =>
          (this.tarefaRecorrenteExecucaosSharedCollection = tarefaRecorrenteExecucaos),
      );
  }
}
