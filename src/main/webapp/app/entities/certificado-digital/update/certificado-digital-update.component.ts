import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { TipoCertificadoEnum } from 'app/entities/enumerations/tipo-certificado-enum.model';
import { CertificadoDigitalService } from '../service/certificado-digital.service';
import { ICertificadoDigital } from '../certificado-digital.model';
import { CertificadoDigitalFormService, CertificadoDigitalFormGroup } from './certificado-digital-form.service';

@Component({
  standalone: true,
  selector: 'jhi-certificado-digital-update',
  templateUrl: './certificado-digital-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CertificadoDigitalUpdateComponent implements OnInit {
  isSaving = false;
  certificadoDigital: ICertificadoDigital | null = null;
  tipoCertificadoEnumValues = Object.keys(TipoCertificadoEnum);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected certificadoDigitalService = inject(CertificadoDigitalService);
  protected certificadoDigitalFormService = inject(CertificadoDigitalFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CertificadoDigitalFormGroup = this.certificadoDigitalFormService.createCertificadoDigitalFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ certificadoDigital }) => {
      this.certificadoDigital = certificadoDigital;
      if (certificadoDigital) {
        this.updateForm(certificadoDigital);
      }
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
    const certificadoDigital = this.certificadoDigitalFormService.getCertificadoDigital(this.editForm);
    if (certificadoDigital.id !== null) {
      this.subscribeToSaveResponse(this.certificadoDigitalService.update(certificadoDigital));
    } else {
      this.subscribeToSaveResponse(this.certificadoDigitalService.create(certificadoDigital));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICertificadoDigital>>): void {
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

  protected updateForm(certificadoDigital: ICertificadoDigital): void {
    this.certificadoDigital = certificadoDigital;
    this.certificadoDigitalFormService.resetForm(this.editForm, certificadoDigital);
  }
}
