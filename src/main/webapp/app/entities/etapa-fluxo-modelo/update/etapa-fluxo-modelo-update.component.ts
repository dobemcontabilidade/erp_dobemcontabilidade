import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFluxoModelo } from 'app/entities/fluxo-modelo/fluxo-modelo.model';
import { FluxoModeloService } from 'app/entities/fluxo-modelo/service/fluxo-modelo.service';
import { IEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';
import { EtapaFluxoModeloService } from '../service/etapa-fluxo-modelo.service';
import { EtapaFluxoModeloFormService, EtapaFluxoModeloFormGroup } from './etapa-fluxo-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-etapa-fluxo-modelo-update',
  templateUrl: './etapa-fluxo-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EtapaFluxoModeloUpdateComponent implements OnInit {
  isSaving = false;
  etapaFluxoModelo: IEtapaFluxoModelo | null = null;

  fluxoModelosSharedCollection: IFluxoModelo[] = [];

  protected etapaFluxoModeloService = inject(EtapaFluxoModeloService);
  protected etapaFluxoModeloFormService = inject(EtapaFluxoModeloFormService);
  protected fluxoModeloService = inject(FluxoModeloService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EtapaFluxoModeloFormGroup = this.etapaFluxoModeloFormService.createEtapaFluxoModeloFormGroup();

  compareFluxoModelo = (o1: IFluxoModelo | null, o2: IFluxoModelo | null): boolean => this.fluxoModeloService.compareFluxoModelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etapaFluxoModelo }) => {
      this.etapaFluxoModelo = etapaFluxoModelo;
      if (etapaFluxoModelo) {
        this.updateForm(etapaFluxoModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etapaFluxoModelo = this.etapaFluxoModeloFormService.getEtapaFluxoModelo(this.editForm);
    if (etapaFluxoModelo.id !== null) {
      this.subscribeToSaveResponse(this.etapaFluxoModeloService.update(etapaFluxoModelo));
    } else {
      this.subscribeToSaveResponse(this.etapaFluxoModeloService.create(etapaFluxoModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtapaFluxoModelo>>): void {
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

  protected updateForm(etapaFluxoModelo: IEtapaFluxoModelo): void {
    this.etapaFluxoModelo = etapaFluxoModelo;
    this.etapaFluxoModeloFormService.resetForm(this.editForm, etapaFluxoModelo);

    this.fluxoModelosSharedCollection = this.fluxoModeloService.addFluxoModeloToCollectionIfMissing<IFluxoModelo>(
      this.fluxoModelosSharedCollection,
      etapaFluxoModelo.fluxoModelo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fluxoModeloService
      .query()
      .pipe(map((res: HttpResponse<IFluxoModelo[]>) => res.body ?? []))
      .pipe(
        map((fluxoModelos: IFluxoModelo[]) =>
          this.fluxoModeloService.addFluxoModeloToCollectionIfMissing<IFluxoModelo>(fluxoModelos, this.etapaFluxoModelo?.fluxoModelo),
        ),
      )
      .subscribe((fluxoModelos: IFluxoModelo[]) => (this.fluxoModelosSharedCollection = fluxoModelos));
  }
}
