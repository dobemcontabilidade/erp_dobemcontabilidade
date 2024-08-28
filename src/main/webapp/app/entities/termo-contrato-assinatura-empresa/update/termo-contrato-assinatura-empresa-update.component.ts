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
import { ITermoContratoContabil } from 'app/entities/termo-contrato-contabil/termo-contrato-contabil.model';
import { TermoContratoContabilService } from 'app/entities/termo-contrato-contabil/service/termo-contrato-contabil.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { SituacaoTermoContratoAssinadoEnum } from 'app/entities/enumerations/situacao-termo-contrato-assinado-enum.model';
import { TermoContratoAssinaturaEmpresaService } from '../service/termo-contrato-assinatura-empresa.service';
import { ITermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';
import {
  TermoContratoAssinaturaEmpresaFormService,
  TermoContratoAssinaturaEmpresaFormGroup,
} from './termo-contrato-assinatura-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-termo-contrato-assinatura-empresa-update',
  templateUrl: './termo-contrato-assinatura-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TermoContratoAssinaturaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  termoContratoAssinaturaEmpresa: ITermoContratoAssinaturaEmpresa | null = null;
  situacaoTermoContratoAssinadoEnumValues = Object.keys(SituacaoTermoContratoAssinadoEnum);

  termoContratoContabilsSharedCollection: ITermoContratoContabil[] = [];
  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected termoContratoAssinaturaEmpresaService = inject(TermoContratoAssinaturaEmpresaService);
  protected termoContratoAssinaturaEmpresaFormService = inject(TermoContratoAssinaturaEmpresaFormService);
  protected termoContratoContabilService = inject(TermoContratoContabilService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TermoContratoAssinaturaEmpresaFormGroup =
    this.termoContratoAssinaturaEmpresaFormService.createTermoContratoAssinaturaEmpresaFormGroup();

  compareTermoContratoContabil = (o1: ITermoContratoContabil | null, o2: ITermoContratoContabil | null): boolean =>
    this.termoContratoContabilService.compareTermoContratoContabil(o1, o2);

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ termoContratoAssinaturaEmpresa }) => {
      this.termoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresa;
      if (termoContratoAssinaturaEmpresa) {
        this.updateForm(termoContratoAssinaturaEmpresa);
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
    const termoContratoAssinaturaEmpresa = this.termoContratoAssinaturaEmpresaFormService.getTermoContratoAssinaturaEmpresa(this.editForm);
    if (termoContratoAssinaturaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.termoContratoAssinaturaEmpresaService.update(termoContratoAssinaturaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.termoContratoAssinaturaEmpresaService.create(termoContratoAssinaturaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITermoContratoAssinaturaEmpresa>>): void {
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

  protected updateForm(termoContratoAssinaturaEmpresa: ITermoContratoAssinaturaEmpresa): void {
    this.termoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresa;
    this.termoContratoAssinaturaEmpresaFormService.resetForm(this.editForm, termoContratoAssinaturaEmpresa);

    this.termoContratoContabilsSharedCollection =
      this.termoContratoContabilService.addTermoContratoContabilToCollectionIfMissing<ITermoContratoContabil>(
        this.termoContratoContabilsSharedCollection,
        termoContratoAssinaturaEmpresa.termoContratoContabil,
      );
    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      termoContratoAssinaturaEmpresa.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.termoContratoContabilService
      .query()
      .pipe(map((res: HttpResponse<ITermoContratoContabil[]>) => res.body ?? []))
      .pipe(
        map((termoContratoContabils: ITermoContratoContabil[]) =>
          this.termoContratoContabilService.addTermoContratoContabilToCollectionIfMissing<ITermoContratoContabil>(
            termoContratoContabils,
            this.termoContratoAssinaturaEmpresa?.termoContratoContabil,
          ),
        ),
      )
      .subscribe(
        (termoContratoContabils: ITermoContratoContabil[]) => (this.termoContratoContabilsSharedCollection = termoContratoContabils),
      );

    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.termoContratoAssinaturaEmpresa?.empresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));
  }
}
