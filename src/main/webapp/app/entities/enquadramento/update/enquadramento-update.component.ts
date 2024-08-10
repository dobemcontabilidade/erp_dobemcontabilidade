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
import { EnquadramentoService } from '../service/enquadramento.service';
import { IEnquadramento } from '../enquadramento.model';
import { EnquadramentoFormService, EnquadramentoFormGroup } from './enquadramento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-enquadramento-update',
  templateUrl: './enquadramento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EnquadramentoUpdateComponent implements OnInit {
  isSaving = false;
  enquadramento: IEnquadramento | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected enquadramentoService = inject(EnquadramentoService);
  protected enquadramentoFormService = inject(EnquadramentoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EnquadramentoFormGroup = this.enquadramentoFormService.createEnquadramentoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquadramento }) => {
      this.enquadramento = enquadramento;
      if (enquadramento) {
        this.updateForm(enquadramento);
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
    const enquadramento = this.enquadramentoFormService.getEnquadramento(this.editForm);
    if (enquadramento.id !== null) {
      this.subscribeToSaveResponse(this.enquadramentoService.update(enquadramento));
    } else {
      this.subscribeToSaveResponse(this.enquadramentoService.create(enquadramento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnquadramento>>): void {
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

  protected updateForm(enquadramento: IEnquadramento): void {
    this.enquadramento = enquadramento;
    this.enquadramentoFormService.resetForm(this.editForm, enquadramento);
  }
}
