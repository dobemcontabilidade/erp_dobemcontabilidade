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
import { TipoDenunciaService } from '../service/tipo-denuncia.service';
import { ITipoDenuncia } from '../tipo-denuncia.model';
import { TipoDenunciaFormService, TipoDenunciaFormGroup } from './tipo-denuncia-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tipo-denuncia-update',
  templateUrl: './tipo-denuncia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TipoDenunciaUpdateComponent implements OnInit {
  isSaving = false;
  tipoDenuncia: ITipoDenuncia | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected tipoDenunciaService = inject(TipoDenunciaService);
  protected tipoDenunciaFormService = inject(TipoDenunciaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TipoDenunciaFormGroup = this.tipoDenunciaFormService.createTipoDenunciaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoDenuncia }) => {
      this.tipoDenuncia = tipoDenuncia;
      if (tipoDenuncia) {
        this.updateForm(tipoDenuncia);
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
    const tipoDenuncia = this.tipoDenunciaFormService.getTipoDenuncia(this.editForm);
    if (tipoDenuncia.id !== null) {
      this.subscribeToSaveResponse(this.tipoDenunciaService.update(tipoDenuncia));
    } else {
      this.subscribeToSaveResponse(this.tipoDenunciaService.create(tipoDenuncia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDenuncia>>): void {
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

  protected updateForm(tipoDenuncia: ITipoDenuncia): void {
    this.tipoDenuncia = tipoDenuncia;
    this.tipoDenunciaFormService.resetForm(this.editForm, tipoDenuncia);
  }
}
