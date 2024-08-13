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
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { AnexoRequeridoEmpresaService } from '../service/anexo-requerido-empresa.service';
import { IAnexoRequeridoEmpresa } from '../anexo-requerido-empresa.model';
import { AnexoRequeridoEmpresaFormService, AnexoRequeridoEmpresaFormGroup } from './anexo-requerido-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-empresa-update',
  templateUrl: './anexo-requerido-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoRequeridoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  anexoRequeridoEmpresa: IAnexoRequeridoEmpresa | null = null;

  anexoRequeridosSharedCollection: IAnexoRequerido[] = [];
  enquadramentosSharedCollection: IEnquadramento[] = [];
  tributacaosSharedCollection: ITributacao[] = [];
  ramosSharedCollection: IRamo[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  empresaModelosSharedCollection: IEmpresaModelo[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected anexoRequeridoEmpresaService = inject(AnexoRequeridoEmpresaService);
  protected anexoRequeridoEmpresaFormService = inject(AnexoRequeridoEmpresaFormService);
  protected anexoRequeridoService = inject(AnexoRequeridoService);
  protected enquadramentoService = inject(EnquadramentoService);
  protected tributacaoService = inject(TributacaoService);
  protected ramoService = inject(RamoService);
  protected empresaService = inject(EmpresaService);
  protected empresaModeloService = inject(EmpresaModeloService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoRequeridoEmpresaFormGroup = this.anexoRequeridoEmpresaFormService.createAnexoRequeridoEmpresaFormGroup();

  compareAnexoRequerido = (o1: IAnexoRequerido | null, o2: IAnexoRequerido | null): boolean =>
    this.anexoRequeridoService.compareAnexoRequerido(o1, o2);

  compareEnquadramento = (o1: IEnquadramento | null, o2: IEnquadramento | null): boolean =>
    this.enquadramentoService.compareEnquadramento(o1, o2);

  compareTributacao = (o1: ITributacao | null, o2: ITributacao | null): boolean => this.tributacaoService.compareTributacao(o1, o2);

  compareRamo = (o1: IRamo | null, o2: IRamo | null): boolean => this.ramoService.compareRamo(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareEmpresaModelo = (o1: IEmpresaModelo | null, o2: IEmpresaModelo | null): boolean =>
    this.empresaModeloService.compareEmpresaModelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoRequeridoEmpresa }) => {
      this.anexoRequeridoEmpresa = anexoRequeridoEmpresa;
      if (anexoRequeridoEmpresa) {
        this.updateForm(anexoRequeridoEmpresa);
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
    const anexoRequeridoEmpresa = this.anexoRequeridoEmpresaFormService.getAnexoRequeridoEmpresa(this.editForm);
    if (anexoRequeridoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.anexoRequeridoEmpresaService.update(anexoRequeridoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.anexoRequeridoEmpresaService.create(anexoRequeridoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoRequeridoEmpresa>>): void {
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

  protected updateForm(anexoRequeridoEmpresa: IAnexoRequeridoEmpresa): void {
    this.anexoRequeridoEmpresa = anexoRequeridoEmpresa;
    this.anexoRequeridoEmpresaFormService.resetForm(this.editForm, anexoRequeridoEmpresa);

    this.anexoRequeridosSharedCollection = this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
      this.anexoRequeridosSharedCollection,
      anexoRequeridoEmpresa.anexoRequerido,
    );
    this.enquadramentosSharedCollection = this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(
      this.enquadramentosSharedCollection,
      anexoRequeridoEmpresa.enquadramento,
    );
    this.tributacaosSharedCollection = this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(
      this.tributacaosSharedCollection,
      anexoRequeridoEmpresa.tributacao,
    );
    this.ramosSharedCollection = this.ramoService.addRamoToCollectionIfMissing<IRamo>(
      this.ramosSharedCollection,
      anexoRequeridoEmpresa.ramo,
    );
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      anexoRequeridoEmpresa.empresa,
    );
    this.empresaModelosSharedCollection = this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
      this.empresaModelosSharedCollection,
      anexoRequeridoEmpresa.empresaModelo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.anexoRequeridoService
      .query()
      .pipe(map((res: HttpResponse<IAnexoRequerido[]>) => res.body ?? []))
      .pipe(
        map((anexoRequeridos: IAnexoRequerido[]) =>
          this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
            anexoRequeridos,
            this.anexoRequeridoEmpresa?.anexoRequerido,
          ),
        ),
      )
      .subscribe((anexoRequeridos: IAnexoRequerido[]) => (this.anexoRequeridosSharedCollection = anexoRequeridos));

    this.enquadramentoService
      .query()
      .pipe(map((res: HttpResponse<IEnquadramento[]>) => res.body ?? []))
      .pipe(
        map((enquadramentos: IEnquadramento[]) =>
          this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(
            enquadramentos,
            this.anexoRequeridoEmpresa?.enquadramento,
          ),
        ),
      )
      .subscribe((enquadramentos: IEnquadramento[]) => (this.enquadramentosSharedCollection = enquadramentos));

    this.tributacaoService
      .query()
      .pipe(map((res: HttpResponse<ITributacao[]>) => res.body ?? []))
      .pipe(
        map((tributacaos: ITributacao[]) =>
          this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(tributacaos, this.anexoRequeridoEmpresa?.tributacao),
        ),
      )
      .subscribe((tributacaos: ITributacao[]) => (this.tributacaosSharedCollection = tributacaos));

    this.ramoService
      .query()
      .pipe(map((res: HttpResponse<IRamo[]>) => res.body ?? []))
      .pipe(map((ramos: IRamo[]) => this.ramoService.addRamoToCollectionIfMissing<IRamo>(ramos, this.anexoRequeridoEmpresa?.ramo)))
      .subscribe((ramos: IRamo[]) => (this.ramosSharedCollection = ramos));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.anexoRequeridoEmpresa?.empresa),
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
            this.anexoRequeridoEmpresa?.empresaModelo,
          ),
        ),
      )
      .subscribe((empresaModelos: IEmpresaModelo[]) => (this.empresaModelosSharedCollection = empresaModelos));
  }
}
