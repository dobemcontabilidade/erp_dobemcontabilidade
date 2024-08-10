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
import { ISecaoCnae } from 'app/entities/secao-cnae/secao-cnae.model';
import { SecaoCnaeService } from 'app/entities/secao-cnae/service/secao-cnae.service';
import { DivisaoCnaeService } from '../service/divisao-cnae.service';
import { IDivisaoCnae } from '../divisao-cnae.model';
import { DivisaoCnaeFormService, DivisaoCnaeFormGroup } from './divisao-cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-divisao-cnae-update',
  templateUrl: './divisao-cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DivisaoCnaeUpdateComponent implements OnInit {
  isSaving = false;
  divisaoCnae: IDivisaoCnae | null = null;

  secaoCnaesSharedCollection: ISecaoCnae[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected divisaoCnaeService = inject(DivisaoCnaeService);
  protected divisaoCnaeFormService = inject(DivisaoCnaeFormService);
  protected secaoCnaeService = inject(SecaoCnaeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DivisaoCnaeFormGroup = this.divisaoCnaeFormService.createDivisaoCnaeFormGroup();

  compareSecaoCnae = (o1: ISecaoCnae | null, o2: ISecaoCnae | null): boolean => this.secaoCnaeService.compareSecaoCnae(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ divisaoCnae }) => {
      this.divisaoCnae = divisaoCnae;
      if (divisaoCnae) {
        this.updateForm(divisaoCnae);
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
    const divisaoCnae = this.divisaoCnaeFormService.getDivisaoCnae(this.editForm);
    if (divisaoCnae.id !== null) {
      this.subscribeToSaveResponse(this.divisaoCnaeService.update(divisaoCnae));
    } else {
      this.subscribeToSaveResponse(this.divisaoCnaeService.create(divisaoCnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDivisaoCnae>>): void {
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

  protected updateForm(divisaoCnae: IDivisaoCnae): void {
    this.divisaoCnae = divisaoCnae;
    this.divisaoCnaeFormService.resetForm(this.editForm, divisaoCnae);

    this.secaoCnaesSharedCollection = this.secaoCnaeService.addSecaoCnaeToCollectionIfMissing<ISecaoCnae>(
      this.secaoCnaesSharedCollection,
      divisaoCnae.secao,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.secaoCnaeService
      .query()
      .pipe(map((res: HttpResponse<ISecaoCnae[]>) => res.body ?? []))
      .pipe(
        map((secaoCnaes: ISecaoCnae[]) =>
          this.secaoCnaeService.addSecaoCnaeToCollectionIfMissing<ISecaoCnae>(secaoCnaes, this.divisaoCnae?.secao),
        ),
      )
      .subscribe((secaoCnaes: ISecaoCnae[]) => (this.secaoCnaesSharedCollection = secaoCnaes));
  }
}
