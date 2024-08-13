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
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { TipoSegmentoEnum } from 'app/entities/enumerations/tipo-segmento-enum.model';
import { SegmentoCnaeService } from '../service/segmento-cnae.service';
import { ISegmentoCnae } from '../segmento-cnae.model';
import { SegmentoCnaeFormService, SegmentoCnaeFormGroup } from './segmento-cnae-form.service';

@Component({
  standalone: true,
  selector: 'jhi-segmento-cnae-update',
  templateUrl: './segmento-cnae-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SegmentoCnaeUpdateComponent implements OnInit {
  isSaving = false;
  segmentoCnae: ISegmentoCnae | null = null;
  tipoSegmentoEnumValues = Object.keys(TipoSegmentoEnum);

  subclasseCnaesSharedCollection: ISubclasseCnae[] = [];
  ramosSharedCollection: IRamo[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  empresaModelosSharedCollection: IEmpresaModelo[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected segmentoCnaeService = inject(SegmentoCnaeService);
  protected segmentoCnaeFormService = inject(SegmentoCnaeFormService);
  protected subclasseCnaeService = inject(SubclasseCnaeService);
  protected ramoService = inject(RamoService);
  protected empresaService = inject(EmpresaService);
  protected empresaModeloService = inject(EmpresaModeloService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SegmentoCnaeFormGroup = this.segmentoCnaeFormService.createSegmentoCnaeFormGroup();

  compareSubclasseCnae = (o1: ISubclasseCnae | null, o2: ISubclasseCnae | null): boolean =>
    this.subclasseCnaeService.compareSubclasseCnae(o1, o2);

  compareRamo = (o1: IRamo | null, o2: IRamo | null): boolean => this.ramoService.compareRamo(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareEmpresaModelo = (o1: IEmpresaModelo | null, o2: IEmpresaModelo | null): boolean =>
    this.empresaModeloService.compareEmpresaModelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ segmentoCnae }) => {
      this.segmentoCnae = segmentoCnae;
      if (segmentoCnae) {
        this.updateForm(segmentoCnae);
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
    const segmentoCnae = this.segmentoCnaeFormService.getSegmentoCnae(this.editForm);
    if (segmentoCnae.id !== null) {
      this.subscribeToSaveResponse(this.segmentoCnaeService.update(segmentoCnae));
    } else {
      this.subscribeToSaveResponse(this.segmentoCnaeService.create(segmentoCnae));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISegmentoCnae>>): void {
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

  protected updateForm(segmentoCnae: ISegmentoCnae): void {
    this.segmentoCnae = segmentoCnae;
    this.segmentoCnaeFormService.resetForm(this.editForm, segmentoCnae);

    this.subclasseCnaesSharedCollection = this.subclasseCnaeService.addSubclasseCnaeToCollectionIfMissing<ISubclasseCnae>(
      this.subclasseCnaesSharedCollection,
      ...(segmentoCnae.subclasseCnaes ?? []),
    );
    this.ramosSharedCollection = this.ramoService.addRamoToCollectionIfMissing<IRamo>(this.ramosSharedCollection, segmentoCnae.ramo);
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      ...(segmentoCnae.empresas ?? []),
    );
    this.empresaModelosSharedCollection = this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
      this.empresaModelosSharedCollection,
      ...(segmentoCnae.empresaModelos ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.subclasseCnaeService
      .query()
      .pipe(map((res: HttpResponse<ISubclasseCnae[]>) => res.body ?? []))
      .pipe(
        map((subclasseCnaes: ISubclasseCnae[]) =>
          this.subclasseCnaeService.addSubclasseCnaeToCollectionIfMissing<ISubclasseCnae>(
            subclasseCnaes,
            ...(this.segmentoCnae?.subclasseCnaes ?? []),
          ),
        ),
      )
      .subscribe((subclasseCnaes: ISubclasseCnae[]) => (this.subclasseCnaesSharedCollection = subclasseCnaes));

    this.ramoService
      .query()
      .pipe(map((res: HttpResponse<IRamo[]>) => res.body ?? []))
      .pipe(map((ramos: IRamo[]) => this.ramoService.addRamoToCollectionIfMissing<IRamo>(ramos, this.segmentoCnae?.ramo)))
      .subscribe((ramos: IRamo[]) => (this.ramosSharedCollection = ramos));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, ...(this.segmentoCnae?.empresas ?? [])),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.empresaModeloService
      .query()
      .pipe(map((res: HttpResponse<IEmpresaModelo[]>) => res.body ?? []))
      .pipe(
        map((empresaModelos: IEmpresaModelo[]) =>
          this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
            empresaModelos,
            ...(this.segmentoCnae?.empresaModelos ?? []),
          ),
        ),
      )
      .subscribe((empresaModelos: IEmpresaModelo[]) => (this.empresaModelosSharedCollection = empresaModelos));
  }
}
