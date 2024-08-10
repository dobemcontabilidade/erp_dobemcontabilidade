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
import { CnaeService } from '../service/cnae.service';
import { ICnae } from '../cnae.model';
import { CnaeFormService, CnaeFormGroup } from './cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cnae-update',
  templateUrl: './cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CnaeUpdateComponent implements OnInit {
  isSaving = false;
  cnae: ICnae | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected cnaeService = inject(CnaeService);
  protected cnaeFormService = inject(CnaeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CnaeFormGroup = this.cnaeFormService.createCnaeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cnae }) => {
      this.cnae = cnae;
      if (cnae) {
        this.updateForm(cnae);
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
    const cnae = this.cnaeFormService.getCnae(this.editForm);
    if (cnae.id !== null) {
      this.subscribeToSaveResponse(this.cnaeService.update(cnae));
    } else {
      this.subscribeToSaveResponse(this.cnaeService.create(cnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICnae>>): void {
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

  protected updateForm(cnae: ICnae): void {
    this.cnae = cnae;
    this.cnaeFormService.resetForm(this.editForm, cnae);
  }
}
