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
import { SecaoCnaeService } from '../service/secao-cnae.service';
import { ISecaoCnae } from '../secao-cnae.model';
import { SecaoCnaeFormService, SecaoCnaeFormGroup } from './secao-cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-secao-cnae-update',
  templateUrl: './secao-cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SecaoCnaeUpdateComponent implements OnInit {
  isSaving = false;
  secaoCnae: ISecaoCnae | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected secaoCnaeService = inject(SecaoCnaeService);
  protected secaoCnaeFormService = inject(SecaoCnaeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SecaoCnaeFormGroup = this.secaoCnaeFormService.createSecaoCnaeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ secaoCnae }) => {
      this.secaoCnae = secaoCnae;
      if (secaoCnae) {
        this.updateForm(secaoCnae);
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
    const secaoCnae = this.secaoCnaeFormService.getSecaoCnae(this.editForm);
    if (secaoCnae.id !== null) {
      this.subscribeToSaveResponse(this.secaoCnaeService.update(secaoCnae));
    } else {
      this.subscribeToSaveResponse(this.secaoCnaeService.create(secaoCnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecaoCnae>>): void {
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

  protected updateForm(secaoCnae: ISecaoCnae): void {
    this.secaoCnae = secaoCnae;
    this.secaoCnaeFormService.resetForm(this.editForm, secaoCnae);
  }
}
