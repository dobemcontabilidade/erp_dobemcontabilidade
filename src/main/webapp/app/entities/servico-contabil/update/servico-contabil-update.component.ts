import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IEsfera } from 'app/entities/esfera/esfera.model';
import { EsferaService } from 'app/entities/esfera/service/esfera.service';
import { ServicoContabilService } from '../service/servico-contabil.service';
import { IServicoContabil } from '../servico-contabil.model';
import { ServicoContabilFormService, ServicoContabilFormGroup } from './servico-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-update',
  templateUrl: './servico-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServicoContabilUpdateComponent implements OnInit {
  isSaving = false;
  servicoContabil: IServicoContabil | null = null;

  areaContabilsSharedCollection: IAreaContabil[] = [];
  esferasSharedCollection: IEsfera[] = [];

  protected servicoContabilService = inject(ServicoContabilService);
  protected servicoContabilFormService = inject(ServicoContabilFormService);
  protected areaContabilService = inject(AreaContabilService);
  protected esferaService = inject(EsferaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServicoContabilFormGroup = this.servicoContabilFormService.createServicoContabilFormGroup();

  compareAreaContabil = (o1: IAreaContabil | null, o2: IAreaContabil | null): boolean =>
    this.areaContabilService.compareAreaContabil(o1, o2);

  compareEsfera = (o1: IEsfera | null, o2: IEsfera | null): boolean => this.esferaService.compareEsfera(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicoContabil }) => {
      this.servicoContabil = servicoContabil;
      if (servicoContabil) {
        this.updateForm(servicoContabil);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicoContabil = this.servicoContabilFormService.getServicoContabil(this.editForm);
    if (servicoContabil.id !== null) {
      this.subscribeToSaveResponse(this.servicoContabilService.update(servicoContabil));
    } else {
      this.subscribeToSaveResponse(this.servicoContabilService.create(servicoContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicoContabil>>): void {
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

  protected updateForm(servicoContabil: IServicoContabil): void {
    this.servicoContabil = servicoContabil;
    this.servicoContabilFormService.resetForm(this.editForm, servicoContabil);

    this.areaContabilsSharedCollection = this.areaContabilService.addAreaContabilToCollectionIfMissing<IAreaContabil>(
      this.areaContabilsSharedCollection,
      servicoContabil.areaContabil,
    );
    this.esferasSharedCollection = this.esferaService.addEsferaToCollectionIfMissing<IEsfera>(
      this.esferasSharedCollection,
      servicoContabil.esfera,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.areaContabilService
      .query()
      .pipe(map((res: HttpResponse<IAreaContabil[]>) => res.body ?? []))
      .pipe(
        map((areaContabils: IAreaContabil[]) =>
          this.areaContabilService.addAreaContabilToCollectionIfMissing<IAreaContabil>(areaContabils, this.servicoContabil?.areaContabil),
        ),
      )
      .subscribe((areaContabils: IAreaContabil[]) => (this.areaContabilsSharedCollection = areaContabils));

    this.esferaService
      .query()
      .pipe(map((res: HttpResponse<IEsfera[]>) => res.body ?? []))
      .pipe(map((esferas: IEsfera[]) => this.esferaService.addEsferaToCollectionIfMissing<IEsfera>(esferas, this.servicoContabil?.esfera)))
      .subscribe((esferas: IEsfera[]) => (this.esferasSharedCollection = esferas));
  }
}
