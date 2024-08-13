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
import { DenunciaService } from '../service/denuncia.service';
import { IDenuncia } from '../denuncia.model';
import { DenunciaFormService, DenunciaFormGroup } from './denuncia-form.service';

@Component({
  standalone: true,
  selector: 'jhi-denuncia-update',
  templateUrl: './denuncia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DenunciaUpdateComponent implements OnInit {
  isSaving = false;
  denuncia: IDenuncia | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected denunciaService = inject(DenunciaService);
  protected denunciaFormService = inject(DenunciaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DenunciaFormGroup = this.denunciaFormService.createDenunciaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ denuncia }) => {
      this.denuncia = denuncia;
      if (denuncia) {
        this.updateForm(denuncia);
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
    const denuncia = this.denunciaFormService.getDenuncia(this.editForm);
    if (denuncia.id !== null) {
      this.subscribeToSaveResponse(this.denunciaService.update(denuncia));
    } else {
      this.subscribeToSaveResponse(this.denunciaService.create(denuncia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDenuncia>>): void {
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

  protected updateForm(denuncia: IDenuncia): void {
    this.denuncia = denuncia;
    this.denunciaFormService.resetForm(this.editForm, denuncia);
  }
}
