import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { AreaContabilAssinaturaEmpresaService } from '../service/area-contabil-assinatura-empresa.service';
import { IAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';
import {
  AreaContabilAssinaturaEmpresaFormService,
  AreaContabilAssinaturaEmpresaFormGroup,
} from './area-contabil-assinatura-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-assinatura-empresa-update',
  templateUrl: './area-contabil-assinatura-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AreaContabilAssinaturaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa | null = null;

  areaContabilsSharedCollection: IAreaContabil[] = [];
  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];
  contadorsSharedCollection: IContador[] = [];

  protected areaContabilAssinaturaEmpresaService = inject(AreaContabilAssinaturaEmpresaService);
  protected areaContabilAssinaturaEmpresaFormService = inject(AreaContabilAssinaturaEmpresaFormService);
  protected areaContabilService = inject(AreaContabilService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected contadorService = inject(ContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AreaContabilAssinaturaEmpresaFormGroup =
    this.areaContabilAssinaturaEmpresaFormService.createAreaContabilAssinaturaEmpresaFormGroup();

  compareAreaContabil = (o1: IAreaContabil | null, o2: IAreaContabil | null): boolean =>
    this.areaContabilService.compareAreaContabil(o1, o2);

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaContabilAssinaturaEmpresa }) => {
      this.areaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresa;
      if (areaContabilAssinaturaEmpresa) {
        this.updateForm(areaContabilAssinaturaEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const areaContabilAssinaturaEmpresa = this.areaContabilAssinaturaEmpresaFormService.getAreaContabilAssinaturaEmpresa(this.editForm);
    if (areaContabilAssinaturaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.areaContabilAssinaturaEmpresaService.update(areaContabilAssinaturaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.areaContabilAssinaturaEmpresaService.create(areaContabilAssinaturaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaContabilAssinaturaEmpresa>>): void {
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

  protected updateForm(areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa): void {
    this.areaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresa;
    this.areaContabilAssinaturaEmpresaFormService.resetForm(this.editForm, areaContabilAssinaturaEmpresa);

    this.areaContabilsSharedCollection = this.areaContabilService.addAreaContabilToCollectionIfMissing<IAreaContabil>(
      this.areaContabilsSharedCollection,
      areaContabilAssinaturaEmpresa.areaContabil,
    );
    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      areaContabilAssinaturaEmpresa.assinaturaEmpresa,
    );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      areaContabilAssinaturaEmpresa.contador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.areaContabilService
      .query()
      .pipe(map((res: HttpResponse<IAreaContabil[]>) => res.body ?? []))
      .pipe(
        map((areaContabils: IAreaContabil[]) =>
          this.areaContabilService.addAreaContabilToCollectionIfMissing<IAreaContabil>(
            areaContabils,
            this.areaContabilAssinaturaEmpresa?.areaContabil,
          ),
        ),
      )
      .subscribe((areaContabils: IAreaContabil[]) => (this.areaContabilsSharedCollection = areaContabils));

    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.areaContabilAssinaturaEmpresa?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.areaContabilAssinaturaEmpresa?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));
  }
}
