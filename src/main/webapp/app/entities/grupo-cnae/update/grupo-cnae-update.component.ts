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
import { IDivisaoCnae } from 'app/entities/divisao-cnae/divisao-cnae.model';
import { DivisaoCnaeService } from 'app/entities/divisao-cnae/service/divisao-cnae.service';
import { GrupoCnaeService } from '../service/grupo-cnae.service';
import { IGrupoCnae } from '../grupo-cnae.model';
import { GrupoCnaeFormService, GrupoCnaeFormGroup } from './grupo-cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-grupo-cnae-update',
  templateUrl: './grupo-cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GrupoCnaeUpdateComponent implements OnInit {
  isSaving = false;
  grupoCnae: IGrupoCnae | null = null;

  divisaoCnaesSharedCollection: IDivisaoCnae[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected grupoCnaeService = inject(GrupoCnaeService);
  protected grupoCnaeFormService = inject(GrupoCnaeFormService);
  protected divisaoCnaeService = inject(DivisaoCnaeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GrupoCnaeFormGroup = this.grupoCnaeFormService.createGrupoCnaeFormGroup();

  compareDivisaoCnae = (o1: IDivisaoCnae | null, o2: IDivisaoCnae | null): boolean => this.divisaoCnaeService.compareDivisaoCnae(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoCnae }) => {
      this.grupoCnae = grupoCnae;
      if (grupoCnae) {
        this.updateForm(grupoCnae);
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
    const grupoCnae = this.grupoCnaeFormService.getGrupoCnae(this.editForm);
    if (grupoCnae.id !== null) {
      this.subscribeToSaveResponse(this.grupoCnaeService.update(grupoCnae));
    } else {
      this.subscribeToSaveResponse(this.grupoCnaeService.create(grupoCnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoCnae>>): void {
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

  protected updateForm(grupoCnae: IGrupoCnae): void {
    this.grupoCnae = grupoCnae;
    this.grupoCnaeFormService.resetForm(this.editForm, grupoCnae);

    this.divisaoCnaesSharedCollection = this.divisaoCnaeService.addDivisaoCnaeToCollectionIfMissing<IDivisaoCnae>(
      this.divisaoCnaesSharedCollection,
      grupoCnae.divisao,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.divisaoCnaeService
      .query()
      .pipe(map((res: HttpResponse<IDivisaoCnae[]>) => res.body ?? []))
      .pipe(
        map((divisaoCnaes: IDivisaoCnae[]) =>
          this.divisaoCnaeService.addDivisaoCnaeToCollectionIfMissing<IDivisaoCnae>(divisaoCnaes, this.grupoCnae?.divisao),
        ),
      )
      .subscribe((divisaoCnaes: IDivisaoCnae[]) => (this.divisaoCnaesSharedCollection = divisaoCnaes));
  }
}
