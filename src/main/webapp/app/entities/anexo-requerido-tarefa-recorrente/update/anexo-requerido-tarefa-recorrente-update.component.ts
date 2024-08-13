import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { ITarefaRecorrente } from 'app/entities/tarefa-recorrente/tarefa-recorrente.model';
import { TarefaRecorrenteService } from 'app/entities/tarefa-recorrente/service/tarefa-recorrente.service';
import { AnexoRequeridoTarefaRecorrenteService } from '../service/anexo-requerido-tarefa-recorrente.service';
import { IAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';
import {
  AnexoRequeridoTarefaRecorrenteFormService,
  AnexoRequeridoTarefaRecorrenteFormGroup,
} from './anexo-requerido-tarefa-recorrente-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-tarefa-recorrente-update',
  templateUrl: './anexo-requerido-tarefa-recorrente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoRequeridoTarefaRecorrenteUpdateComponent implements OnInit {
  isSaving = false;
  anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente | null = null;

  anexoRequeridosSharedCollection: IAnexoRequerido[] = [];
  tarefaRecorrentesSharedCollection: ITarefaRecorrente[] = [];

  protected anexoRequeridoTarefaRecorrenteService = inject(AnexoRequeridoTarefaRecorrenteService);
  protected anexoRequeridoTarefaRecorrenteFormService = inject(AnexoRequeridoTarefaRecorrenteFormService);
  protected anexoRequeridoService = inject(AnexoRequeridoService);
  protected tarefaRecorrenteService = inject(TarefaRecorrenteService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoRequeridoTarefaRecorrenteFormGroup =
    this.anexoRequeridoTarefaRecorrenteFormService.createAnexoRequeridoTarefaRecorrenteFormGroup();

  compareAnexoRequerido = (o1: IAnexoRequerido | null, o2: IAnexoRequerido | null): boolean =>
    this.anexoRequeridoService.compareAnexoRequerido(o1, o2);

  compareTarefaRecorrente = (o1: ITarefaRecorrente | null, o2: ITarefaRecorrente | null): boolean =>
    this.tarefaRecorrenteService.compareTarefaRecorrente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoRequeridoTarefaRecorrente }) => {
      this.anexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrente;
      if (anexoRequeridoTarefaRecorrente) {
        this.updateForm(anexoRequeridoTarefaRecorrente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexoRequeridoTarefaRecorrente = this.anexoRequeridoTarefaRecorrenteFormService.getAnexoRequeridoTarefaRecorrente(this.editForm);
    if (anexoRequeridoTarefaRecorrente.id !== null) {
      this.subscribeToSaveResponse(this.anexoRequeridoTarefaRecorrenteService.update(anexoRequeridoTarefaRecorrente));
    } else {
      this.subscribeToSaveResponse(this.anexoRequeridoTarefaRecorrenteService.create(anexoRequeridoTarefaRecorrente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoRequeridoTarefaRecorrente>>): void {
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

  protected updateForm(anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente): void {
    this.anexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrente;
    this.anexoRequeridoTarefaRecorrenteFormService.resetForm(this.editForm, anexoRequeridoTarefaRecorrente);

    this.anexoRequeridosSharedCollection = this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
      this.anexoRequeridosSharedCollection,
      anexoRequeridoTarefaRecorrente.anexoRequerido,
    );
    this.tarefaRecorrentesSharedCollection = this.tarefaRecorrenteService.addTarefaRecorrenteToCollectionIfMissing<ITarefaRecorrente>(
      this.tarefaRecorrentesSharedCollection,
      anexoRequeridoTarefaRecorrente.tarefaRecorrente,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.anexoRequeridoService
      .query()
      .pipe(map((res: HttpResponse<IAnexoRequerido[]>) => res.body ?? []))
      .pipe(
        map((anexoRequeridos: IAnexoRequerido[]) =>
          this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
            anexoRequeridos,
            this.anexoRequeridoTarefaRecorrente?.anexoRequerido,
          ),
        ),
      )
      .subscribe((anexoRequeridos: IAnexoRequerido[]) => (this.anexoRequeridosSharedCollection = anexoRequeridos));

    this.tarefaRecorrenteService
      .query()
      .pipe(map((res: HttpResponse<ITarefaRecorrente[]>) => res.body ?? []))
      .pipe(
        map((tarefaRecorrentes: ITarefaRecorrente[]) =>
          this.tarefaRecorrenteService.addTarefaRecorrenteToCollectionIfMissing<ITarefaRecorrente>(
            tarefaRecorrentes,
            this.anexoRequeridoTarefaRecorrente?.tarefaRecorrente,
          ),
        ),
      )
      .subscribe((tarefaRecorrentes: ITarefaRecorrente[]) => (this.tarefaRecorrentesSharedCollection = tarefaRecorrentes));
  }
}
