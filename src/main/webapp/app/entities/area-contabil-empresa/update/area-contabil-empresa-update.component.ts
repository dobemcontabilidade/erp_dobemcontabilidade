import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';
import { AreaContabilEmpresaService } from '../service/area-contabil-empresa.service';
import { AreaContabilEmpresaFormService, AreaContabilEmpresaFormGroup } from './area-contabil-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-empresa-update',
  templateUrl: './area-contabil-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AreaContabilEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  areaContabilEmpresa: IAreaContabilEmpresa | null = null;

  contadorsSharedCollection: IContador[] = [];

  protected areaContabilEmpresaService = inject(AreaContabilEmpresaService);
  protected areaContabilEmpresaFormService = inject(AreaContabilEmpresaFormService);
  protected contadorService = inject(ContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AreaContabilEmpresaFormGroup = this.areaContabilEmpresaFormService.createAreaContabilEmpresaFormGroup();

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaContabilEmpresa }) => {
      this.areaContabilEmpresa = areaContabilEmpresa;
      if (areaContabilEmpresa) {
        this.updateForm(areaContabilEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const areaContabilEmpresa = this.areaContabilEmpresaFormService.getAreaContabilEmpresa(this.editForm);
    if (areaContabilEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.areaContabilEmpresaService.update(areaContabilEmpresa));
    } else {
      this.subscribeToSaveResponse(this.areaContabilEmpresaService.create(areaContabilEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaContabilEmpresa>>): void {
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

  protected updateForm(areaContabilEmpresa: IAreaContabilEmpresa): void {
    this.areaContabilEmpresa = areaContabilEmpresa;
    this.areaContabilEmpresaFormService.resetForm(this.editForm, areaContabilEmpresa);

    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      areaContabilEmpresa.contador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.areaContabilEmpresa?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));
  }
}
