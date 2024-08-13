import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { ServicoContabilOrdemServicoService } from '../service/servico-contabil-ordem-servico.service';
import { IServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';
import {
  ServicoContabilOrdemServicoFormService,
  ServicoContabilOrdemServicoFormGroup,
} from './servico-contabil-ordem-servico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-ordem-servico-update',
  templateUrl: './servico-contabil-ordem-servico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServicoContabilOrdemServicoUpdateComponent implements OnInit {
  isSaving = false;
  servicoContabilOrdemServico: IServicoContabilOrdemServico | null = null;

  servicoContabilsSharedCollection: IServicoContabil[] = [];
  ordemServicosSharedCollection: IOrdemServico[] = [];

  protected servicoContabilOrdemServicoService = inject(ServicoContabilOrdemServicoService);
  protected servicoContabilOrdemServicoFormService = inject(ServicoContabilOrdemServicoFormService);
  protected servicoContabilService = inject(ServicoContabilService);
  protected ordemServicoService = inject(OrdemServicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServicoContabilOrdemServicoFormGroup = this.servicoContabilOrdemServicoFormService.createServicoContabilOrdemServicoFormGroup();

  compareServicoContabil = (o1: IServicoContabil | null, o2: IServicoContabil | null): boolean =>
    this.servicoContabilService.compareServicoContabil(o1, o2);

  compareOrdemServico = (o1: IOrdemServico | null, o2: IOrdemServico | null): boolean =>
    this.ordemServicoService.compareOrdemServico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicoContabilOrdemServico }) => {
      this.servicoContabilOrdemServico = servicoContabilOrdemServico;
      if (servicoContabilOrdemServico) {
        this.updateForm(servicoContabilOrdemServico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicoContabilOrdemServico = this.servicoContabilOrdemServicoFormService.getServicoContabilOrdemServico(this.editForm);
    if (servicoContabilOrdemServico.id !== null) {
      this.subscribeToSaveResponse(this.servicoContabilOrdemServicoService.update(servicoContabilOrdemServico));
    } else {
      this.subscribeToSaveResponse(this.servicoContabilOrdemServicoService.create(servicoContabilOrdemServico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicoContabilOrdemServico>>): void {
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

  protected updateForm(servicoContabilOrdemServico: IServicoContabilOrdemServico): void {
    this.servicoContabilOrdemServico = servicoContabilOrdemServico;
    this.servicoContabilOrdemServicoFormService.resetForm(this.editForm, servicoContabilOrdemServico);

    this.servicoContabilsSharedCollection = this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
      this.servicoContabilsSharedCollection,
      servicoContabilOrdemServico.servicoContabil,
    );
    this.ordemServicosSharedCollection = this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
      this.ordemServicosSharedCollection,
      servicoContabilOrdemServico.ordemServico,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.servicoContabilService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabil[]>) => res.body ?? []))
      .pipe(
        map((servicoContabils: IServicoContabil[]) =>
          this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
            servicoContabils,
            this.servicoContabilOrdemServico?.servicoContabil,
          ),
        ),
      )
      .subscribe((servicoContabils: IServicoContabil[]) => (this.servicoContabilsSharedCollection = servicoContabils));

    this.ordemServicoService
      .query()
      .pipe(map((res: HttpResponse<IOrdemServico[]>) => res.body ?? []))
      .pipe(
        map((ordemServicos: IOrdemServico[]) =>
          this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
            ordemServicos,
            this.servicoContabilOrdemServico?.ordemServico,
          ),
        ),
      )
      .subscribe((ordemServicos: IOrdemServico[]) => (this.ordemServicosSharedCollection = ordemServicos));
  }
}
