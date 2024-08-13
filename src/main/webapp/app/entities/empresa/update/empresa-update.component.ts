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
import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { SegmentoCnaeService } from 'app/entities/segmento-cnae/service/segmento-cnae.service';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { EmpresaService } from '../service/empresa.service';
import { IEmpresa } from '../empresa.model';
import { EmpresaFormService, EmpresaFormGroup } from './empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-empresa-update',
  templateUrl: './empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmpresaUpdateComponent implements OnInit {
  isSaving = false;
  empresa: IEmpresa | null = null;

  segmentoCnaesSharedCollection: ISegmentoCnae[] = [];
  empresaModelosSharedCollection: IEmpresaModelo[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected empresaService = inject(EmpresaService);
  protected empresaFormService = inject(EmpresaFormService);
  protected segmentoCnaeService = inject(SegmentoCnaeService);
  protected empresaModeloService = inject(EmpresaModeloService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpresaFormGroup = this.empresaFormService.createEmpresaFormGroup();

  compareSegmentoCnae = (o1: ISegmentoCnae | null, o2: ISegmentoCnae | null): boolean =>
    this.segmentoCnaeService.compareSegmentoCnae(o1, o2);

  compareEmpresaModelo = (o1: IEmpresaModelo | null, o2: IEmpresaModelo | null): boolean =>
    this.empresaModeloService.compareEmpresaModelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.empresa = empresa;
      if (empresa) {
        this.updateForm(empresa);
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
    const empresa = this.empresaFormService.getEmpresa(this.editForm);
    if (empresa.id !== null) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>): void {
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

  protected updateForm(empresa: IEmpresa): void {
    this.empresa = empresa;
    this.empresaFormService.resetForm(this.editForm, empresa);

    this.segmentoCnaesSharedCollection = this.segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing<ISegmentoCnae>(
      this.segmentoCnaesSharedCollection,
      ...(empresa.segmentoCnaes ?? []),
    );
    this.empresaModelosSharedCollection = this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
      this.empresaModelosSharedCollection,
      empresa.empresaModelo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.segmentoCnaeService
      .query()
      .pipe(map((res: HttpResponse<ISegmentoCnae[]>) => res.body ?? []))
      .pipe(
        map((segmentoCnaes: ISegmentoCnae[]) =>
          this.segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing<ISegmentoCnae>(
            segmentoCnaes,
            ...(this.empresa?.segmentoCnaes ?? []),
          ),
        ),
      )
      .subscribe((segmentoCnaes: ISegmentoCnae[]) => (this.segmentoCnaesSharedCollection = segmentoCnaes));

    this.empresaModeloService
      .query()
      .pipe(map((res: HttpResponse<IEmpresaModelo[]>) => res.body ?? []))
      .pipe(
        map((empresaModelos: IEmpresaModelo[]) =>
          this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(empresaModelos, this.empresa?.empresaModelo),
        ),
      )
      .subscribe((empresaModelos: IEmpresaModelo[]) => (this.empresaModelosSharedCollection = empresaModelos));
  }
}
