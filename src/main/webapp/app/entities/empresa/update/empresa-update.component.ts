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
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { TipoSegmentoEnum } from 'app/entities/enumerations/tipo-segmento-enum.model';
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
  tipoSegmentoEnumValues = Object.keys(TipoSegmentoEnum);

  ramosSharedCollection: IRamo[] = [];
  tributacaosSharedCollection: ITributacao[] = [];
  enquadramentosSharedCollection: IEnquadramento[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected empresaService = inject(EmpresaService);
  protected empresaFormService = inject(EmpresaFormService);
  protected ramoService = inject(RamoService);
  protected tributacaoService = inject(TributacaoService);
  protected enquadramentoService = inject(EnquadramentoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpresaFormGroup = this.empresaFormService.createEmpresaFormGroup();

  compareRamo = (o1: IRamo | null, o2: IRamo | null): boolean => this.ramoService.compareRamo(o1, o2);

  compareTributacao = (o1: ITributacao | null, o2: ITributacao | null): boolean => this.tributacaoService.compareTributacao(o1, o2);

  compareEnquadramento = (o1: IEnquadramento | null, o2: IEnquadramento | null): boolean =>
    this.enquadramentoService.compareEnquadramento(o1, o2);

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

    this.ramosSharedCollection = this.ramoService.addRamoToCollectionIfMissing<IRamo>(this.ramosSharedCollection, empresa.ramo);
    this.tributacaosSharedCollection = this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(
      this.tributacaosSharedCollection,
      empresa.tributacao,
    );
    this.enquadramentosSharedCollection = this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(
      this.enquadramentosSharedCollection,
      empresa.enquadramento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ramoService
      .query()
      .pipe(map((res: HttpResponse<IRamo[]>) => res.body ?? []))
      .pipe(map((ramos: IRamo[]) => this.ramoService.addRamoToCollectionIfMissing<IRamo>(ramos, this.empresa?.ramo)))
      .subscribe((ramos: IRamo[]) => (this.ramosSharedCollection = ramos));

    this.tributacaoService
      .query()
      .pipe(map((res: HttpResponse<ITributacao[]>) => res.body ?? []))
      .pipe(
        map((tributacaos: ITributacao[]) =>
          this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(tributacaos, this.empresa?.tributacao),
        ),
      )
      .subscribe((tributacaos: ITributacao[]) => (this.tributacaosSharedCollection = tributacaos));

    this.enquadramentoService
      .query()
      .pipe(map((res: HttpResponse<IEnquadramento[]>) => res.body ?? []))
      .pipe(
        map((enquadramentos: IEnquadramento[]) =>
          this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(enquadramentos, this.empresa?.enquadramento),
        ),
      )
      .subscribe((enquadramentos: IEnquadramento[]) => (this.enquadramentosSharedCollection = enquadramentos));
  }
}
