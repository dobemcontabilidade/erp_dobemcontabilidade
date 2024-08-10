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
import { RamoService } from '../service/ramo.service';
import { IRamo } from '../ramo.model';
import { RamoFormService, RamoFormGroup } from './ramo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-ramo-update',
  templateUrl: './ramo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RamoUpdateComponent implements OnInit {
  isSaving = false;
  ramo: IRamo | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected ramoService = inject(RamoService);
  protected ramoFormService = inject(RamoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RamoFormGroup = this.ramoFormService.createRamoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ramo }) => {
      this.ramo = ramo;
      if (ramo) {
        this.updateForm(ramo);
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
    const ramo = this.ramoFormService.getRamo(this.editForm);
    if (ramo.id !== null) {
      this.subscribeToSaveResponse(this.ramoService.update(ramo));
    } else {
      this.subscribeToSaveResponse(this.ramoService.create(ramo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRamo>>): void {
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

  protected updateForm(ramo: IRamo): void {
    this.ramo = ramo;
    this.ramoFormService.resetForm(this.editForm, ramo);
  }
}
