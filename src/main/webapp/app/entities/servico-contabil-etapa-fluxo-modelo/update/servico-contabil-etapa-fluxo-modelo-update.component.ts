import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEtapaFluxoModelo } from 'app/entities/etapa-fluxo-modelo/etapa-fluxo-modelo.model';
import { EtapaFluxoModeloService } from 'app/entities/etapa-fluxo-modelo/service/etapa-fluxo-modelo.service';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { ServicoContabilEtapaFluxoModeloService } from '../service/servico-contabil-etapa-fluxo-modelo.service';
import { IServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';
import {
  ServicoContabilEtapaFluxoModeloFormService,
  ServicoContabilEtapaFluxoModeloFormGroup,
} from './servico-contabil-etapa-fluxo-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-etapa-fluxo-modelo-update',
  templateUrl: './servico-contabil-etapa-fluxo-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServicoContabilEtapaFluxoModeloUpdateComponent implements OnInit {
  isSaving = false;
  servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo | null = null;

  etapaFluxoModelosSharedCollection: IEtapaFluxoModelo[] = [];
  servicoContabilsSharedCollection: IServicoContabil[] = [];

  protected servicoContabilEtapaFluxoModeloService = inject(ServicoContabilEtapaFluxoModeloService);
  protected servicoContabilEtapaFluxoModeloFormService = inject(ServicoContabilEtapaFluxoModeloFormService);
  protected etapaFluxoModeloService = inject(EtapaFluxoModeloService);
  protected servicoContabilService = inject(ServicoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServicoContabilEtapaFluxoModeloFormGroup =
    this.servicoContabilEtapaFluxoModeloFormService.createServicoContabilEtapaFluxoModeloFormGroup();

  compareEtapaFluxoModelo = (o1: IEtapaFluxoModelo | null, o2: IEtapaFluxoModelo | null): boolean =>
    this.etapaFluxoModeloService.compareEtapaFluxoModelo(o1, o2);

  compareServicoContabil = (o1: IServicoContabil | null, o2: IServicoContabil | null): boolean =>
    this.servicoContabilService.compareServicoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicoContabilEtapaFluxoModelo }) => {
      this.servicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModelo;
      if (servicoContabilEtapaFluxoModelo) {
        this.updateForm(servicoContabilEtapaFluxoModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicoContabilEtapaFluxoModelo = this.servicoContabilEtapaFluxoModeloFormService.getServicoContabilEtapaFluxoModelo(
      this.editForm,
    );
    if (servicoContabilEtapaFluxoModelo.id !== null) {
      this.subscribeToSaveResponse(this.servicoContabilEtapaFluxoModeloService.update(servicoContabilEtapaFluxoModelo));
    } else {
      this.subscribeToSaveResponse(this.servicoContabilEtapaFluxoModeloService.create(servicoContabilEtapaFluxoModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicoContabilEtapaFluxoModelo>>): void {
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

  protected updateForm(servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo): void {
    this.servicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModelo;
    this.servicoContabilEtapaFluxoModeloFormService.resetForm(this.editForm, servicoContabilEtapaFluxoModelo);

    this.etapaFluxoModelosSharedCollection = this.etapaFluxoModeloService.addEtapaFluxoModeloToCollectionIfMissing<IEtapaFluxoModelo>(
      this.etapaFluxoModelosSharedCollection,
      servicoContabilEtapaFluxoModelo.etapaFluxoModelo,
    );
    this.servicoContabilsSharedCollection = this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
      this.servicoContabilsSharedCollection,
      servicoContabilEtapaFluxoModelo.servicoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.etapaFluxoModeloService
      .query()
      .pipe(map((res: HttpResponse<IEtapaFluxoModelo[]>) => res.body ?? []))
      .pipe(
        map((etapaFluxoModelos: IEtapaFluxoModelo[]) =>
          this.etapaFluxoModeloService.addEtapaFluxoModeloToCollectionIfMissing<IEtapaFluxoModelo>(
            etapaFluxoModelos,
            this.servicoContabilEtapaFluxoModelo?.etapaFluxoModelo,
          ),
        ),
      )
      .subscribe((etapaFluxoModelos: IEtapaFluxoModelo[]) => (this.etapaFluxoModelosSharedCollection = etapaFluxoModelos));

    this.servicoContabilService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabil[]>) => res.body ?? []))
      .pipe(
        map((servicoContabils: IServicoContabil[]) =>
          this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
            servicoContabils,
            this.servicoContabilEtapaFluxoModelo?.servicoContabil,
          ),
        ),
      )
      .subscribe((servicoContabils: IServicoContabil[]) => (this.servicoContabilsSharedCollection = servicoContabils));
  }
}
