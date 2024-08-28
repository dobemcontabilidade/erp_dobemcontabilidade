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
import { FornecedorCertificadoService } from '../service/fornecedor-certificado.service';
import { IFornecedorCertificado } from '../fornecedor-certificado.model';
import { FornecedorCertificadoFormService, FornecedorCertificadoFormGroup } from './fornecedor-certificado-form.service';

@Component({
  standalone: true,
  selector: 'jhi-fornecedor-certificado-update',
  templateUrl: './fornecedor-certificado-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FornecedorCertificadoUpdateComponent implements OnInit {
  isSaving = false;
  fornecedorCertificado: IFornecedorCertificado | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected fornecedorCertificadoService = inject(FornecedorCertificadoService);
  protected fornecedorCertificadoFormService = inject(FornecedorCertificadoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FornecedorCertificadoFormGroup = this.fornecedorCertificadoFormService.createFornecedorCertificadoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fornecedorCertificado }) => {
      this.fornecedorCertificado = fornecedorCertificado;
      if (fornecedorCertificado) {
        this.updateForm(fornecedorCertificado);
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
    const fornecedorCertificado = this.fornecedorCertificadoFormService.getFornecedorCertificado(this.editForm);
    if (fornecedorCertificado.id !== null) {
      this.subscribeToSaveResponse(this.fornecedorCertificadoService.update(fornecedorCertificado));
    } else {
      this.subscribeToSaveResponse(this.fornecedorCertificadoService.create(fornecedorCertificado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedorCertificado>>): void {
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

  protected updateForm(fornecedorCertificado: IFornecedorCertificado): void {
    this.fornecedorCertificado = fornecedorCertificado;
    this.fornecedorCertificadoFormService.resetForm(this.editForm, fornecedorCertificado);
  }
}
