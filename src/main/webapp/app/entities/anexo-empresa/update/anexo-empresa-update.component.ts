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
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IAnexoRequeridoEmpresa } from 'app/entities/anexo-requerido-empresa/anexo-requerido-empresa.model';
import { AnexoRequeridoEmpresaService } from 'app/entities/anexo-requerido-empresa/service/anexo-requerido-empresa.service';
import { AnexoEmpresaService } from '../service/anexo-empresa.service';
import { IAnexoEmpresa } from '../anexo-empresa.model';
import { AnexoEmpresaFormService, AnexoEmpresaFormGroup } from './anexo-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-empresa-update',
  templateUrl: './anexo-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  anexoEmpresa: IAnexoEmpresa | null = null;

  empresasSharedCollection: IEmpresa[] = [];
  anexoRequeridoEmpresasSharedCollection: IAnexoRequeridoEmpresa[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected anexoEmpresaService = inject(AnexoEmpresaService);
  protected anexoEmpresaFormService = inject(AnexoEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected anexoRequeridoEmpresaService = inject(AnexoRequeridoEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoEmpresaFormGroup = this.anexoEmpresaFormService.createAnexoEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareAnexoRequeridoEmpresa = (o1: IAnexoRequeridoEmpresa | null, o2: IAnexoRequeridoEmpresa | null): boolean =>
    this.anexoRequeridoEmpresaService.compareAnexoRequeridoEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoEmpresa }) => {
      this.anexoEmpresa = anexoEmpresa;
      if (anexoEmpresa) {
        this.updateForm(anexoEmpresa);
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
    const anexoEmpresa = this.anexoEmpresaFormService.getAnexoEmpresa(this.editForm);
    if (anexoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.anexoEmpresaService.update(anexoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.anexoEmpresaService.create(anexoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoEmpresa>>): void {
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

  protected updateForm(anexoEmpresa: IAnexoEmpresa): void {
    this.anexoEmpresa = anexoEmpresa;
    this.anexoEmpresaFormService.resetForm(this.editForm, anexoEmpresa);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      anexoEmpresa.empresa,
    );
    this.anexoRequeridoEmpresasSharedCollection =
      this.anexoRequeridoEmpresaService.addAnexoRequeridoEmpresaToCollectionIfMissing<IAnexoRequeridoEmpresa>(
        this.anexoRequeridoEmpresasSharedCollection,
        anexoEmpresa.anexoRequeridoEmpresa,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.anexoEmpresa?.empresa)),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.anexoRequeridoEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAnexoRequeridoEmpresa[]>) => res.body ?? []))
      .pipe(
        map((anexoRequeridoEmpresas: IAnexoRequeridoEmpresa[]) =>
          this.anexoRequeridoEmpresaService.addAnexoRequeridoEmpresaToCollectionIfMissing<IAnexoRequeridoEmpresa>(
            anexoRequeridoEmpresas,
            this.anexoEmpresa?.anexoRequeridoEmpresa,
          ),
        ),
      )
      .subscribe(
        (anexoRequeridoEmpresas: IAnexoRequeridoEmpresa[]) => (this.anexoRequeridoEmpresasSharedCollection = anexoRequeridoEmpresas),
      );
  }
}
