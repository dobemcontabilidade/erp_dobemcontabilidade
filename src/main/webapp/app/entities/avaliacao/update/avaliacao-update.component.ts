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
import { AvaliacaoService } from '../service/avaliacao.service';
import { IAvaliacao } from '../avaliacao.model';
import { AvaliacaoFormService, AvaliacaoFormGroup } from './avaliacao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-avaliacao-update',
  templateUrl: './avaliacao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AvaliacaoUpdateComponent implements OnInit {
  isSaving = false;
  avaliacao: IAvaliacao | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected avaliacaoService = inject(AvaliacaoService);
  protected avaliacaoFormService = inject(AvaliacaoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AvaliacaoFormGroup = this.avaliacaoFormService.createAvaliacaoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avaliacao }) => {
      this.avaliacao = avaliacao;
      if (avaliacao) {
        this.updateForm(avaliacao);
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
    const avaliacao = this.avaliacaoFormService.getAvaliacao(this.editForm);
    if (avaliacao.id !== null) {
      this.subscribeToSaveResponse(this.avaliacaoService.update(avaliacao));
    } else {
      this.subscribeToSaveResponse(this.avaliacaoService.create(avaliacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvaliacao>>): void {
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

  protected updateForm(avaliacao: IAvaliacao): void {
    this.avaliacao = avaliacao;
    this.avaliacaoFormService.resetForm(this.editForm, avaliacao);
  }
}
