import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { FrequenciaService } from '../service/frequencia.service';
import { IFrequencia } from '../frequencia.model';
import { FrequenciaFormService, FrequenciaFormGroup } from './frequencia-form.service';

@Component({
  standalone: true,
  selector: 'jhi-frequencia-update',
  templateUrl: './frequencia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FrequenciaUpdateComponent implements OnInit {
  isSaving = false;
  frequencia: IFrequencia | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected frequenciaService = inject(FrequenciaService);
  protected frequenciaFormService = inject(FrequenciaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FrequenciaFormGroup = this.frequenciaFormService.createFrequenciaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ frequencia }) => {
      this.frequencia = frequencia;
      if (frequencia) {
        this.updateForm(frequencia);
      }
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
    const frequencia = this.frequenciaFormService.getFrequencia(this.editForm);
    if (frequencia.id !== null) {
      this.subscribeToSaveResponse(this.frequenciaService.update(frequencia));
    } else {
      this.subscribeToSaveResponse(this.frequenciaService.create(frequencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFrequencia>>): void {
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

  protected updateForm(frequencia: IFrequencia): void {
    this.frequencia = frequencia;
    this.frequenciaFormService.resetForm(this.editForm, frequencia);
  }
}
