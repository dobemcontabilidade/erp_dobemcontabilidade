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
import { TermoDeAdesaoService } from '../service/termo-de-adesao.service';
import { ITermoDeAdesao } from '../termo-de-adesao.model';
import { TermoDeAdesaoFormService, TermoDeAdesaoFormGroup } from './termo-de-adesao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-termo-de-adesao-update',
  templateUrl: './termo-de-adesao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TermoDeAdesaoUpdateComponent implements OnInit {
  isSaving = false;
  termoDeAdesao: ITermoDeAdesao | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected termoDeAdesaoService = inject(TermoDeAdesaoService);
  protected termoDeAdesaoFormService = inject(TermoDeAdesaoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TermoDeAdesaoFormGroup = this.termoDeAdesaoFormService.createTermoDeAdesaoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ termoDeAdesao }) => {
      this.termoDeAdesao = termoDeAdesao;
      if (termoDeAdesao) {
        this.updateForm(termoDeAdesao);
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
    const termoDeAdesao = this.termoDeAdesaoFormService.getTermoDeAdesao(this.editForm);
    if (termoDeAdesao.id !== null) {
      this.subscribeToSaveResponse(this.termoDeAdesaoService.update(termoDeAdesao));
    } else {
      this.subscribeToSaveResponse(this.termoDeAdesaoService.create(termoDeAdesao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITermoDeAdesao>>): void {
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

  protected updateForm(termoDeAdesao: ITermoDeAdesao): void {
    this.termoDeAdesao = termoDeAdesao;
    this.termoDeAdesaoFormService.resetForm(this.editForm, termoDeAdesao);
  }
}
