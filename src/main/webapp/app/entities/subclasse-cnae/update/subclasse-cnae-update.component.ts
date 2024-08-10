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
import { IClasseCnae } from 'app/entities/classe-cnae/classe-cnae.model';
import { ClasseCnaeService } from 'app/entities/classe-cnae/service/classe-cnae.service';
import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { SegmentoCnaeService } from 'app/entities/segmento-cnae/service/segmento-cnae.service';
import { SubclasseCnaeService } from '../service/subclasse-cnae.service';
import { ISubclasseCnae } from '../subclasse-cnae.model';
import { SubclasseCnaeFormService, SubclasseCnaeFormGroup } from './subclasse-cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-subclasse-cnae-update',
  templateUrl: './subclasse-cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SubclasseCnaeUpdateComponent implements OnInit {
  isSaving = false;
  subclasseCnae: ISubclasseCnae | null = null;

  classeCnaesSharedCollection: IClasseCnae[] = [];
  segmentoCnaesSharedCollection: ISegmentoCnae[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected subclasseCnaeService = inject(SubclasseCnaeService);
  protected subclasseCnaeFormService = inject(SubclasseCnaeFormService);
  protected classeCnaeService = inject(ClasseCnaeService);
  protected segmentoCnaeService = inject(SegmentoCnaeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SubclasseCnaeFormGroup = this.subclasseCnaeFormService.createSubclasseCnaeFormGroup();

  compareClasseCnae = (o1: IClasseCnae | null, o2: IClasseCnae | null): boolean => this.classeCnaeService.compareClasseCnae(o1, o2);

  compareSegmentoCnae = (o1: ISegmentoCnae | null, o2: ISegmentoCnae | null): boolean =>
    this.segmentoCnaeService.compareSegmentoCnae(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subclasseCnae }) => {
      this.subclasseCnae = subclasseCnae;
      if (subclasseCnae) {
        this.updateForm(subclasseCnae);
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
    const subclasseCnae = this.subclasseCnaeFormService.getSubclasseCnae(this.editForm);
    if (subclasseCnae.id !== null) {
      this.subscribeToSaveResponse(this.subclasseCnaeService.update(subclasseCnae));
    } else {
      this.subscribeToSaveResponse(this.subclasseCnaeService.create(subclasseCnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubclasseCnae>>): void {
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

  protected updateForm(subclasseCnae: ISubclasseCnae): void {
    this.subclasseCnae = subclasseCnae;
    this.subclasseCnaeFormService.resetForm(this.editForm, subclasseCnae);

    this.classeCnaesSharedCollection = this.classeCnaeService.addClasseCnaeToCollectionIfMissing<IClasseCnae>(
      this.classeCnaesSharedCollection,
      subclasseCnae.classe,
    );
    this.segmentoCnaesSharedCollection = this.segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing<ISegmentoCnae>(
      this.segmentoCnaesSharedCollection,
      ...(subclasseCnae.segmentoCnaes ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.classeCnaeService
      .query()
      .pipe(map((res: HttpResponse<IClasseCnae[]>) => res.body ?? []))
      .pipe(
        map((classeCnaes: IClasseCnae[]) =>
          this.classeCnaeService.addClasseCnaeToCollectionIfMissing<IClasseCnae>(classeCnaes, this.subclasseCnae?.classe),
        ),
      )
      .subscribe((classeCnaes: IClasseCnae[]) => (this.classeCnaesSharedCollection = classeCnaes));

    this.segmentoCnaeService
      .query()
      .pipe(map((res: HttpResponse<ISegmentoCnae[]>) => res.body ?? []))
      .pipe(
        map((segmentoCnaes: ISegmentoCnae[]) =>
          this.segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing<ISegmentoCnae>(
            segmentoCnaes,
            ...(this.subclasseCnae?.segmentoCnaes ?? []),
          ),
        ),
      )
      .subscribe((segmentoCnaes: ISegmentoCnae[]) => (this.segmentoCnaesSharedCollection = segmentoCnaes));
  }
}
