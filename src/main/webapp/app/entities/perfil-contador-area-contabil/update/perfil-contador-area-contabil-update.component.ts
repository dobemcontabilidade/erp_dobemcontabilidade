import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';
import { PerfilContadorService } from 'app/entities/perfil-contador/service/perfil-contador.service';
import { PerfilContadorAreaContabilService } from '../service/perfil-contador-area-contabil.service';
import { IPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';
import { PerfilContadorAreaContabilFormService, PerfilContadorAreaContabilFormGroup } from './perfil-contador-area-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-perfil-contador-area-contabil-update',
  templateUrl: './perfil-contador-area-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PerfilContadorAreaContabilUpdateComponent implements OnInit {
  isSaving = false;
  perfilContadorAreaContabil: IPerfilContadorAreaContabil | null = null;

  areaContabilsSharedCollection: IAreaContabil[] = [];
  perfilContadorsSharedCollection: IPerfilContador[] = [];

  protected perfilContadorAreaContabilService = inject(PerfilContadorAreaContabilService);
  protected perfilContadorAreaContabilFormService = inject(PerfilContadorAreaContabilFormService);
  protected areaContabilService = inject(AreaContabilService);
  protected perfilContadorService = inject(PerfilContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PerfilContadorAreaContabilFormGroup = this.perfilContadorAreaContabilFormService.createPerfilContadorAreaContabilFormGroup();

  compareAreaContabil = (o1: IAreaContabil | null, o2: IAreaContabil | null): boolean =>
    this.areaContabilService.compareAreaContabil(o1, o2);

  comparePerfilContador = (o1: IPerfilContador | null, o2: IPerfilContador | null): boolean =>
    this.perfilContadorService.comparePerfilContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilContadorAreaContabil }) => {
      this.perfilContadorAreaContabil = perfilContadorAreaContabil;
      if (perfilContadorAreaContabil) {
        this.updateForm(perfilContadorAreaContabil);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilContadorAreaContabil = this.perfilContadorAreaContabilFormService.getPerfilContadorAreaContabil(this.editForm);
    if (perfilContadorAreaContabil.id !== null) {
      this.subscribeToSaveResponse(this.perfilContadorAreaContabilService.update(perfilContadorAreaContabil));
    } else {
      this.subscribeToSaveResponse(this.perfilContadorAreaContabilService.create(perfilContadorAreaContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilContadorAreaContabil>>): void {
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

  protected updateForm(perfilContadorAreaContabil: IPerfilContadorAreaContabil): void {
    this.perfilContadorAreaContabil = perfilContadorAreaContabil;
    this.perfilContadorAreaContabilFormService.resetForm(this.editForm, perfilContadorAreaContabil);

    this.areaContabilsSharedCollection = this.areaContabilService.addAreaContabilToCollectionIfMissing<IAreaContabil>(
      this.areaContabilsSharedCollection,
      perfilContadorAreaContabil.areaContabil,
    );
    this.perfilContadorsSharedCollection = this.perfilContadorService.addPerfilContadorToCollectionIfMissing<IPerfilContador>(
      this.perfilContadorsSharedCollection,
      perfilContadorAreaContabil.perfilContador,
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
            this.perfilContadorAreaContabil?.areaContabil,
          ),
        ),
      )
      .subscribe((areaContabils: IAreaContabil[]) => (this.areaContabilsSharedCollection = areaContabils));

    this.perfilContadorService
      .query()
      .pipe(map((res: HttpResponse<IPerfilContador[]>) => res.body ?? []))
      .pipe(
        map((perfilContadors: IPerfilContador[]) =>
          this.perfilContadorService.addPerfilContadorToCollectionIfMissing<IPerfilContador>(
            perfilContadors,
            this.perfilContadorAreaContabil?.perfilContador,
          ),
        ),
      )
      .subscribe((perfilContadors: IPerfilContador[]) => (this.perfilContadorsSharedCollection = perfilContadors));
  }
}
