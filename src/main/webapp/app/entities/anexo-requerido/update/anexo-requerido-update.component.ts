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
import { TipoAnexoRequeridoEnum } from 'app/entities/enumerations/tipo-anexo-requerido-enum.model';
import { AnexoRequeridoService } from '../service/anexo-requerido.service';
import { IAnexoRequerido } from '../anexo-requerido.model';
import { AnexoRequeridoFormService, AnexoRequeridoFormGroup } from './anexo-requerido-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-update',
  templateUrl: './anexo-requerido-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoRequeridoUpdateComponent implements OnInit {
  isSaving = false;
  anexoRequerido: IAnexoRequerido | null = null;
  tipoAnexoRequeridoEnumValues = Object.keys(TipoAnexoRequeridoEnum);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected anexoRequeridoService = inject(AnexoRequeridoService);
  protected anexoRequeridoFormService = inject(AnexoRequeridoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoRequeridoFormGroup = this.anexoRequeridoFormService.createAnexoRequeridoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoRequerido }) => {
      this.anexoRequerido = anexoRequerido;
      if (anexoRequerido) {
        this.updateForm(anexoRequerido);
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
    const anexoRequerido = this.anexoRequeridoFormService.getAnexoRequerido(this.editForm);
    if (anexoRequerido.id !== null) {
      this.subscribeToSaveResponse(this.anexoRequeridoService.update(anexoRequerido));
    } else {
      this.subscribeToSaveResponse(this.anexoRequeridoService.create(anexoRequerido));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoRequerido>>): void {
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

  protected updateForm(anexoRequerido: IAnexoRequerido): void {
    this.anexoRequerido = anexoRequerido;
    this.anexoRequeridoFormService.resetForm(this.editForm, anexoRequerido);
  }
}
