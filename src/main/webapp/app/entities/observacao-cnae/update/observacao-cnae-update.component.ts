import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';
import { SubclasseCnaeService } from 'app/entities/subclasse-cnae/service/subclasse-cnae.service';
import { ObservacaoCnaeService } from '../service/observacao-cnae.service';
import { IObservacaoCnae } from '../observacao-cnae.model';
import { ObservacaoCnaeFormService, ObservacaoCnaeFormGroup } from './observacao-cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-observacao-cnae-update',
  templateUrl: './observacao-cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ObservacaoCnaeUpdateComponent implements OnInit {
  isSaving = false;
  observacaoCnae: IObservacaoCnae | null = null;

  subclasseCnaesSharedCollection: ISubclasseCnae[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected observacaoCnaeService = inject(ObservacaoCnaeService);
  protected observacaoCnaeFormService = inject(ObservacaoCnaeFormService);
  protected subclasseCnaeService = inject(SubclasseCnaeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ObservacaoCnaeFormGroup = this.observacaoCnaeFormService.createObservacaoCnaeFormGroup();

  compareSubclasseCnae = (o1: ISubclasseCnae | null, o2: ISubclasseCnae | null): boolean =>
    this.subclasseCnaeService.compareSubclasseCnae(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observacaoCnae }) => {
      this.observacaoCnae = observacaoCnae;
      if (observacaoCnae) {
        this.updateForm(observacaoCnae);
      }

      this.loadRelationshipsOptions();
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
    const observacaoCnae = this.observacaoCnaeFormService.getObservacaoCnae(this.editForm);
    if (observacaoCnae.id !== null) {
      this.subscribeToSaveResponse(this.observacaoCnaeService.update(observacaoCnae));
    } else {
      this.subscribeToSaveResponse(this.observacaoCnaeService.create(observacaoCnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservacaoCnae>>): void {
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

  protected updateForm(observacaoCnae: IObservacaoCnae): void {
    this.observacaoCnae = observacaoCnae;
    this.observacaoCnaeFormService.resetForm(this.editForm, observacaoCnae);

    this.subclasseCnaesSharedCollection = this.subclasseCnaeService.addSubclasseCnaeToCollectionIfMissing<ISubclasseCnae>(
      this.subclasseCnaesSharedCollection,
      observacaoCnae.subclasse,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.subclasseCnaeService
      .query()
      .pipe(map((res: HttpResponse<ISubclasseCnae[]>) => res.body ?? []))
      .pipe(
        map((subclasseCnaes: ISubclasseCnae[]) =>
          this.subclasseCnaeService.addSubclasseCnaeToCollectionIfMissing<ISubclasseCnae>(subclasseCnaes, this.observacaoCnae?.subclasse),
        ),
      )
      .subscribe((subclasseCnaes: ISubclasseCnae[]) => (this.subclasseCnaesSharedCollection = subclasseCnaes));
  }
}
