import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { ContadorResponsavelTarefaRecorrenteService } from '../service/contador-responsavel-tarefa-recorrente.service';
import { IContadorResponsavelTarefaRecorrente } from '../contador-responsavel-tarefa-recorrente.model';
import {
  ContadorResponsavelTarefaRecorrenteFormService,
  ContadorResponsavelTarefaRecorrenteFormGroup,
} from './contador-responsavel-tarefa-recorrente-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contador-responsavel-tarefa-recorrente-update',
  templateUrl: './contador-responsavel-tarefa-recorrente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContadorResponsavelTarefaRecorrenteUpdateComponent implements OnInit {
  isSaving = false;
  contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente | null = null;

  tarefaRecorrenteExecucaosSharedCollection: ITarefaRecorrenteExecucao[] = [];
  contadorsSharedCollection: IContador[] = [];

  protected contadorResponsavelTarefaRecorrenteService = inject(ContadorResponsavelTarefaRecorrenteService);
  protected contadorResponsavelTarefaRecorrenteFormService = inject(ContadorResponsavelTarefaRecorrenteFormService);
  protected tarefaRecorrenteExecucaoService = inject(TarefaRecorrenteExecucaoService);
  protected contadorService = inject(ContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContadorResponsavelTarefaRecorrenteFormGroup =
    this.contadorResponsavelTarefaRecorrenteFormService.createContadorResponsavelTarefaRecorrenteFormGroup();

  compareTarefaRecorrenteExecucao = (o1: ITarefaRecorrenteExecucao | null, o2: ITarefaRecorrenteExecucao | null): boolean =>
    this.tarefaRecorrenteExecucaoService.compareTarefaRecorrenteExecucao(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contadorResponsavelTarefaRecorrente }) => {
      this.contadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrente;
      if (contadorResponsavelTarefaRecorrente) {
        this.updateForm(contadorResponsavelTarefaRecorrente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contadorResponsavelTarefaRecorrente = this.contadorResponsavelTarefaRecorrenteFormService.getContadorResponsavelTarefaRecorrente(
      this.editForm,
    );
    if (contadorResponsavelTarefaRecorrente.id !== null) {
      this.subscribeToSaveResponse(this.contadorResponsavelTarefaRecorrenteService.update(contadorResponsavelTarefaRecorrente));
    } else {
      this.subscribeToSaveResponse(this.contadorResponsavelTarefaRecorrenteService.create(contadorResponsavelTarefaRecorrente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContadorResponsavelTarefaRecorrente>>): void {
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

  protected updateForm(contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente): void {
    this.contadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrente;
    this.contadorResponsavelTarefaRecorrenteFormService.resetForm(this.editForm, contadorResponsavelTarefaRecorrente);

    this.tarefaRecorrenteExecucaosSharedCollection =
      this.tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing<ITarefaRecorrenteExecucao>(
        this.tarefaRecorrenteExecucaosSharedCollection,
        contadorResponsavelTarefaRecorrente.tarefaRecorrenteExecucao,
      );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      contadorResponsavelTarefaRecorrente.contador,
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
            this.contadorResponsavelTarefaRecorrente?.tarefaRecorrenteExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaRecorrenteExecucaos: ITarefaRecorrenteExecucao[]) =>
          (this.tarefaRecorrenteExecucaosSharedCollection = tarefaRecorrenteExecucaos),
      );

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.contadorResponsavelTarefaRecorrente?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));
  }
}
