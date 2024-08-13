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
import { IGrupoCnae } from 'app/entities/grupo-cnae/grupo-cnae.model';
import { GrupoCnaeService } from 'app/entities/grupo-cnae/service/grupo-cnae.service';
import { ClasseCnaeService } from '../service/classe-cnae.service';
import { IClasseCnae } from '../classe-cnae.model';
import { ClasseCnaeFormService, ClasseCnaeFormGroup } from './classe-cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-classe-cnae-update',
  templateUrl: './classe-cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ClasseCnaeUpdateComponent implements OnInit {
  isSaving = false;
  classeCnae: IClasseCnae | null = null;

  grupoCnaesSharedCollection: IGrupoCnae[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected classeCnaeService = inject(ClasseCnaeService);
  protected classeCnaeFormService = inject(ClasseCnaeFormService);
  protected grupoCnaeService = inject(GrupoCnaeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ClasseCnaeFormGroup = this.classeCnaeFormService.createClasseCnaeFormGroup();

  compareGrupoCnae = (o1: IGrupoCnae | null, o2: IGrupoCnae | null): boolean => this.grupoCnaeService.compareGrupoCnae(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classeCnae }) => {
      this.classeCnae = classeCnae;
      if (classeCnae) {
        this.updateForm(classeCnae);
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
    const classeCnae = this.classeCnaeFormService.getClasseCnae(this.editForm);
    if (classeCnae.id !== null) {
      this.subscribeToSaveResponse(this.classeCnaeService.update(classeCnae));
    } else {
      this.subscribeToSaveResponse(this.classeCnaeService.create(classeCnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasseCnae>>): void {
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

  protected updateForm(classeCnae: IClasseCnae): void {
    this.classeCnae = classeCnae;
    this.classeCnaeFormService.resetForm(this.editForm, classeCnae);

    this.grupoCnaesSharedCollection = this.grupoCnaeService.addGrupoCnaeToCollectionIfMissing<IGrupoCnae>(
      this.grupoCnaesSharedCollection,
      classeCnae.grupo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.grupoCnaeService
      .query()
      .pipe(map((res: HttpResponse<IGrupoCnae[]>) => res.body ?? []))
      .pipe(
        map((grupoCnaes: IGrupoCnae[]) =>
          this.grupoCnaeService.addGrupoCnaeToCollectionIfMissing<IGrupoCnae>(grupoCnaes, this.classeCnae?.grupo),
        ),
      )
      .subscribe((grupoCnaes: IGrupoCnae[]) => (this.grupoCnaesSharedCollection = grupoCnaes));
  }
}
