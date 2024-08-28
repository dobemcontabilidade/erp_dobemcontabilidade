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
import { RedeSocialService } from '../service/rede-social.service';
import { IRedeSocial } from '../rede-social.model';
import { RedeSocialFormService, RedeSocialFormGroup } from './rede-social-form.service';

@Component({
  standalone: true,
  selector: 'jhi-rede-social-update',
  templateUrl: './rede-social-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RedeSocialUpdateComponent implements OnInit {
  isSaving = false;
  redeSocial: IRedeSocial | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected redeSocialService = inject(RedeSocialService);
  protected redeSocialFormService = inject(RedeSocialFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RedeSocialFormGroup = this.redeSocialFormService.createRedeSocialFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ redeSocial }) => {
      this.redeSocial = redeSocial;
      if (redeSocial) {
        this.updateForm(redeSocial);
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
    const redeSocial = this.redeSocialFormService.getRedeSocial(this.editForm);
    if (redeSocial.id !== null) {
      this.subscribeToSaveResponse(this.redeSocialService.update(redeSocial));
    } else {
      this.subscribeToSaveResponse(this.redeSocialService.create(redeSocial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRedeSocial>>): void {
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

  protected updateForm(redeSocial: IRedeSocial): void {
    this.redeSocial = redeSocial;
    this.redeSocialFormService.resetForm(this.editForm, redeSocial);
  }
}
