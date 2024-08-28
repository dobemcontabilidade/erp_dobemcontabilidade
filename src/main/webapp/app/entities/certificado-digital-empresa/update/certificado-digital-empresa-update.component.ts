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
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { ICertificadoDigital } from 'app/entities/certificado-digital/certificado-digital.model';
import { CertificadoDigitalService } from 'app/entities/certificado-digital/service/certificado-digital.service';
import { IFornecedorCertificado } from 'app/entities/fornecedor-certificado/fornecedor-certificado.model';
import { FornecedorCertificadoService } from 'app/entities/fornecedor-certificado/service/fornecedor-certificado.service';
import { CertificadoDigitalEmpresaService } from '../service/certificado-digital-empresa.service';
import { ICertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';
import { CertificadoDigitalEmpresaFormService, CertificadoDigitalEmpresaFormGroup } from './certificado-digital-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-certificado-digital-empresa-update',
  templateUrl: './certificado-digital-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CertificadoDigitalEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  certificadoDigitalEmpresa: ICertificadoDigitalEmpresa | null = null;

  pessoajuridicasSharedCollection: IPessoajuridica[] = [];
  certificadoDigitalsSharedCollection: ICertificadoDigital[] = [];
  fornecedorCertificadosSharedCollection: IFornecedorCertificado[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected certificadoDigitalEmpresaService = inject(CertificadoDigitalEmpresaService);
  protected certificadoDigitalEmpresaFormService = inject(CertificadoDigitalEmpresaFormService);
  protected pessoajuridicaService = inject(PessoajuridicaService);
  protected certificadoDigitalService = inject(CertificadoDigitalService);
  protected fornecedorCertificadoService = inject(FornecedorCertificadoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CertificadoDigitalEmpresaFormGroup = this.certificadoDigitalEmpresaFormService.createCertificadoDigitalEmpresaFormGroup();

  comparePessoajuridica = (o1: IPessoajuridica | null, o2: IPessoajuridica | null): boolean =>
    this.pessoajuridicaService.comparePessoajuridica(o1, o2);

  compareCertificadoDigital = (o1: ICertificadoDigital | null, o2: ICertificadoDigital | null): boolean =>
    this.certificadoDigitalService.compareCertificadoDigital(o1, o2);

  compareFornecedorCertificado = (o1: IFornecedorCertificado | null, o2: IFornecedorCertificado | null): boolean =>
    this.fornecedorCertificadoService.compareFornecedorCertificado(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ certificadoDigitalEmpresa }) => {
      this.certificadoDigitalEmpresa = certificadoDigitalEmpresa;
      if (certificadoDigitalEmpresa) {
        this.updateForm(certificadoDigitalEmpresa);
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
    const certificadoDigitalEmpresa = this.certificadoDigitalEmpresaFormService.getCertificadoDigitalEmpresa(this.editForm);
    if (certificadoDigitalEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.certificadoDigitalEmpresaService.update(certificadoDigitalEmpresa));
    } else {
      this.subscribeToSaveResponse(this.certificadoDigitalEmpresaService.create(certificadoDigitalEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICertificadoDigitalEmpresa>>): void {
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

  protected updateForm(certificadoDigitalEmpresa: ICertificadoDigitalEmpresa): void {
    this.certificadoDigitalEmpresa = certificadoDigitalEmpresa;
    this.certificadoDigitalEmpresaFormService.resetForm(this.editForm, certificadoDigitalEmpresa);

    this.pessoajuridicasSharedCollection = this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
      this.pessoajuridicasSharedCollection,
      certificadoDigitalEmpresa.pessoaJuridica,
    );
    this.certificadoDigitalsSharedCollection =
      this.certificadoDigitalService.addCertificadoDigitalToCollectionIfMissing<ICertificadoDigital>(
        this.certificadoDigitalsSharedCollection,
        certificadoDigitalEmpresa.certificadoDigital,
      );
    this.fornecedorCertificadosSharedCollection =
      this.fornecedorCertificadoService.addFornecedorCertificadoToCollectionIfMissing<IFornecedorCertificado>(
        this.fornecedorCertificadosSharedCollection,
        certificadoDigitalEmpresa.fornecedorCertificado,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoajuridicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoajuridica[]>) => res.body ?? []))
      .pipe(
        map((pessoajuridicas: IPessoajuridica[]) =>
          this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
            pessoajuridicas,
            this.certificadoDigitalEmpresa?.pessoaJuridica,
          ),
        ),
      )
      .subscribe((pessoajuridicas: IPessoajuridica[]) => (this.pessoajuridicasSharedCollection = pessoajuridicas));

    this.certificadoDigitalService
      .query()
      .pipe(map((res: HttpResponse<ICertificadoDigital[]>) => res.body ?? []))
      .pipe(
        map((certificadoDigitals: ICertificadoDigital[]) =>
          this.certificadoDigitalService.addCertificadoDigitalToCollectionIfMissing<ICertificadoDigital>(
            certificadoDigitals,
            this.certificadoDigitalEmpresa?.certificadoDigital,
          ),
        ),
      )
      .subscribe((certificadoDigitals: ICertificadoDigital[]) => (this.certificadoDigitalsSharedCollection = certificadoDigitals));

    this.fornecedorCertificadoService
      .query()
      .pipe(map((res: HttpResponse<IFornecedorCertificado[]>) => res.body ?? []))
      .pipe(
        map((fornecedorCertificados: IFornecedorCertificado[]) =>
          this.fornecedorCertificadoService.addFornecedorCertificadoToCollectionIfMissing<IFornecedorCertificado>(
            fornecedorCertificados,
            this.certificadoDigitalEmpresa?.fornecedorCertificado,
          ),
        ),
      )
      .subscribe(
        (fornecedorCertificados: IFornecedorCertificado[]) => (this.fornecedorCertificadosSharedCollection = fornecedorCertificados),
      );
  }
}
