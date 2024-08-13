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
import { EscolaridadeService } from '../service/escolaridade.service';
import { IEscolaridade } from '../escolaridade.model';
import { EscolaridadeFormService, EscolaridadeFormGroup } from './escolaridade-form.service';

@Component({
  standalone: true,
  selector: 'jhi-escolaridade-update',
  templateUrl: './escolaridade-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EscolaridadeUpdateComponent implements OnInit {
  isSaving = false;
  escolaridade: IEscolaridade | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected escolaridadeService = inject(EscolaridadeService);
  protected escolaridadeFormService = inject(EscolaridadeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EscolaridadeFormGroup = this.escolaridadeFormService.createEscolaridadeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ escolaridade }) => {
      this.escolaridade = escolaridade;
      if (escolaridade) {
        this.updateForm(escolaridade);
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
    const escolaridade = this.escolaridadeFormService.getEscolaridade(this.editForm);
    if (escolaridade.id !== null) {
      this.subscribeToSaveResponse(this.escolaridadeService.update(escolaridade));
    } else {
      this.subscribeToSaveResponse(this.escolaridadeService.create(escolaridade));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEscolaridade>>): void {
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

  protected updateForm(escolaridade: IEscolaridade): void {
    this.escolaridade = escolaridade;
    this.escolaridadeFormService.resetForm(this.editForm, escolaridade);
  }
}
