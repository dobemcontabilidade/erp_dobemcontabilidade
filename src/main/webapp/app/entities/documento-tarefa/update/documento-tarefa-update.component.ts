import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefa } from 'app/entities/tarefa/tarefa.model';
import { TarefaService } from 'app/entities/tarefa/service/tarefa.service';
import { IDocumentoTarefa } from '../documento-tarefa.model';
import { DocumentoTarefaService } from '../service/documento-tarefa.service';
import { DocumentoTarefaFormService, DocumentoTarefaFormGroup } from './documento-tarefa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-documento-tarefa-update',
  templateUrl: './documento-tarefa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DocumentoTarefaUpdateComponent implements OnInit {
  isSaving = false;
  documentoTarefa: IDocumentoTarefa | null = null;

  tarefasSharedCollection: ITarefa[] = [];

  protected documentoTarefaService = inject(DocumentoTarefaService);
  protected documentoTarefaFormService = inject(DocumentoTarefaFormService);
  protected tarefaService = inject(TarefaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DocumentoTarefaFormGroup = this.documentoTarefaFormService.createDocumentoTarefaFormGroup();

  compareTarefa = (o1: ITarefa | null, o2: ITarefa | null): boolean => this.tarefaService.compareTarefa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentoTarefa }) => {
      this.documentoTarefa = documentoTarefa;
      if (documentoTarefa) {
        this.updateForm(documentoTarefa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentoTarefa = this.documentoTarefaFormService.getDocumentoTarefa(this.editForm);
    if (documentoTarefa.id !== null) {
      this.subscribeToSaveResponse(this.documentoTarefaService.update(documentoTarefa));
    } else {
      this.subscribeToSaveResponse(this.documentoTarefaService.create(documentoTarefa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentoTarefa>>): void {
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

  protected updateForm(documentoTarefa: IDocumentoTarefa): void {
    this.documentoTarefa = documentoTarefa;
    this.documentoTarefaFormService.resetForm(this.editForm, documentoTarefa);

    this.tarefasSharedCollection = this.tarefaService.addTarefaToCollectionIfMissing<ITarefa>(
      this.tarefasSharedCollection,
      documentoTarefa.tarefa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tarefaService
      .query()
      .pipe(map((res: HttpResponse<ITarefa[]>) => res.body ?? []))
      .pipe(map((tarefas: ITarefa[]) => this.tarefaService.addTarefaToCollectionIfMissing<ITarefa>(tarefas, this.documentoTarefa?.tarefa)))
      .subscribe((tarefas: ITarefa[]) => (this.tarefasSharedCollection = tarefas));
  }
}
