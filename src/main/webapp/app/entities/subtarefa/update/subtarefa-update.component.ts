import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ITarefa } from 'app/entities/tarefa/tarefa.model';
import { TarefaService } from 'app/entities/tarefa/service/tarefa.service';
import { SubtarefaService } from '../service/subtarefa.service';
import { ISubtarefa } from '../subtarefa.model';
import { SubtarefaFormService, SubtarefaFormGroup } from './subtarefa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-subtarefa-update',
  templateUrl: './subtarefa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SubtarefaUpdateComponent implements OnInit {
  isSaving = false;
  subtarefa: ISubtarefa | null = null;

  tarefasSharedCollection: ITarefa[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected subtarefaService = inject(SubtarefaService);
  protected subtarefaFormService = inject(SubtarefaFormService);
  protected tarefaService = inject(TarefaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SubtarefaFormGroup = this.subtarefaFormService.createSubtarefaFormGroup();

  compareTarefa = (o1: ITarefa | null, o2: ITarefa | null): boolean => this.tarefaService.compareTarefa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subtarefa }) => {
      this.subtarefa = subtarefa;
      if (subtarefa) {
        this.updateForm(subtarefa);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('erpDobemcontabilidadeApp.error', { ...err, key: 'error.file.' + err.key }),
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subtarefa = this.subtarefaFormService.getSubtarefa(this.editForm);
    if (subtarefa.id !== null) {
      this.subscribeToSaveResponse(this.subtarefaService.update(subtarefa));
    } else {
      this.subscribeToSaveResponse(this.subtarefaService.create(subtarefa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubtarefa>>): void {
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

  protected updateForm(subtarefa: ISubtarefa): void {
    this.subtarefa = subtarefa;
    this.subtarefaFormService.resetForm(this.editForm, subtarefa);

    this.tarefasSharedCollection = this.tarefaService.addTarefaToCollectionIfMissing<ITarefa>(
      this.tarefasSharedCollection,
      subtarefa.tarefa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tarefaService
      .query()
      .pipe(map((res: HttpResponse<ITarefa[]>) => res.body ?? []))
      .pipe(map((tarefas: ITarefa[]) => this.tarefaService.addTarefaToCollectionIfMissing<ITarefa>(tarefas, this.subtarefa?.tarefa)))
      .subscribe((tarefas: ITarefa[]) => (this.tarefasSharedCollection = tarefas));
  }
}
