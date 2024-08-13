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
import { EsferaService } from '../service/esfera.service';
import { IEsfera } from '../esfera.model';
import { EsferaFormService, EsferaFormGroup } from './esfera-form.service';

@Component({
  standalone: true,
  selector: 'jhi-esfera-update',
  templateUrl: './esfera-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EsferaUpdateComponent implements OnInit {
  isSaving = false;
  esfera: IEsfera | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected esferaService = inject(EsferaService);
  protected esferaFormService = inject(EsferaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EsferaFormGroup = this.esferaFormService.createEsferaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ esfera }) => {
      this.esfera = esfera;
      if (esfera) {
        this.updateForm(esfera);
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
    const esfera = this.esferaFormService.getEsfera(this.editForm);
    if (esfera.id !== null) {
      this.subscribeToSaveResponse(this.esferaService.update(esfera));
    } else {
      this.subscribeToSaveResponse(this.esferaService.create(esfera));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEsfera>>): void {
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

  protected updateForm(esfera: IEsfera): void {
    this.esfera = esfera;
    this.esferaFormService.resetForm(this.editForm, esfera);
  }
}
