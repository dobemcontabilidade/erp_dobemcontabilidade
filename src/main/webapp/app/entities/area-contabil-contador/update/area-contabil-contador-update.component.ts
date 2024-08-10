import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { AreaContabilContadorService } from '../service/area-contabil-contador.service';
import { IAreaContabilContador } from '../area-contabil-contador.model';
import { AreaContabilContadorFormService, AreaContabilContadorFormGroup } from './area-contabil-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-contador-update',
  templateUrl: './area-contabil-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AreaContabilContadorUpdateComponent implements OnInit {
  isSaving = false;
  areaContabilContador: IAreaContabilContador | null = null;

  areaContabilsSharedCollection: IAreaContabil[] = [];
  contadorsSharedCollection: IContador[] = [];

  protected areaContabilContadorService = inject(AreaContabilContadorService);
  protected areaContabilContadorFormService = inject(AreaContabilContadorFormService);
  protected areaContabilService = inject(AreaContabilService);
  protected contadorService = inject(ContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AreaContabilContadorFormGroup = this.areaContabilContadorFormService.createAreaContabilContadorFormGroup();

  compareAreaContabil = (o1: IAreaContabil | null, o2: IAreaContabil | null): boolean =>
    this.areaContabilService.compareAreaContabil(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaContabilContador }) => {
      this.areaContabilContador = areaContabilContador;
      if (areaContabilContador) {
        this.updateForm(areaContabilContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const areaContabilContador = this.areaContabilContadorFormService.getAreaContabilContador(this.editForm);
    if (areaContabilContador.id !== null) {
      this.subscribeToSaveResponse(this.areaContabilContadorService.update(areaContabilContador));
    } else {
      this.subscribeToSaveResponse(this.areaContabilContadorService.create(areaContabilContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaContabilContador>>): void {
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

  protected updateForm(areaContabilContador: IAreaContabilContador): void {
    this.areaContabilContador = areaContabilContador;
    this.areaContabilContadorFormService.resetForm(this.editForm, areaContabilContador);

    this.areaContabilsSharedCollection = this.areaContabilService.addAreaContabilToCollectionIfMissing<IAreaContabil>(
      this.areaContabilsSharedCollection,
      areaContabilContador.areaContabil,
    );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      areaContabilContador.contador,
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
            this.areaContabilContador?.areaContabil,
          ),
        ),
      )
      .subscribe((areaContabils: IAreaContabil[]) => (this.areaContabilsSharedCollection = areaContabils));

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.areaContabilContador?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));
  }
}
