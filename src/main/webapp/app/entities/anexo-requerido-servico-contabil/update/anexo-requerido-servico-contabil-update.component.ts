import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { AnexoRequeridoServicoContabilService } from '../service/anexo-requerido-servico-contabil.service';
import { IAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';
import {
  AnexoRequeridoServicoContabilFormService,
  AnexoRequeridoServicoContabilFormGroup,
} from './anexo-requerido-servico-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-servico-contabil-update',
  templateUrl: './anexo-requerido-servico-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoRequeridoServicoContabilUpdateComponent implements OnInit {
  isSaving = false;
  anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil | null = null;

  servicoContabilsSharedCollection: IServicoContabil[] = [];
  anexoRequeridosSharedCollection: IAnexoRequerido[] = [];

  protected anexoRequeridoServicoContabilService = inject(AnexoRequeridoServicoContabilService);
  protected anexoRequeridoServicoContabilFormService = inject(AnexoRequeridoServicoContabilFormService);
  protected servicoContabilService = inject(ServicoContabilService);
  protected anexoRequeridoService = inject(AnexoRequeridoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoRequeridoServicoContabilFormGroup =
    this.anexoRequeridoServicoContabilFormService.createAnexoRequeridoServicoContabilFormGroup();

  compareServicoContabil = (o1: IServicoContabil | null, o2: IServicoContabil | null): boolean =>
    this.servicoContabilService.compareServicoContabil(o1, o2);

  compareAnexoRequerido = (o1: IAnexoRequerido | null, o2: IAnexoRequerido | null): boolean =>
    this.anexoRequeridoService.compareAnexoRequerido(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoRequeridoServicoContabil }) => {
      this.anexoRequeridoServicoContabil = anexoRequeridoServicoContabil;
      if (anexoRequeridoServicoContabil) {
        this.updateForm(anexoRequeridoServicoContabil);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexoRequeridoServicoContabil = this.anexoRequeridoServicoContabilFormService.getAnexoRequeridoServicoContabil(this.editForm);
    if (anexoRequeridoServicoContabil.id !== null) {
      this.subscribeToSaveResponse(this.anexoRequeridoServicoContabilService.update(anexoRequeridoServicoContabil));
    } else {
      this.subscribeToSaveResponse(this.anexoRequeridoServicoContabilService.create(anexoRequeridoServicoContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoRequeridoServicoContabil>>): void {
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

  protected updateForm(anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil): void {
    this.anexoRequeridoServicoContabil = anexoRequeridoServicoContabil;
    this.anexoRequeridoServicoContabilFormService.resetForm(this.editForm, anexoRequeridoServicoContabil);

    this.servicoContabilsSharedCollection = this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
      this.servicoContabilsSharedCollection,
      anexoRequeridoServicoContabil.servicoContabil,
    );
    this.anexoRequeridosSharedCollection = this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
      this.anexoRequeridosSharedCollection,
      anexoRequeridoServicoContabil.anexoRequerido,
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
            this.anexoRequeridoServicoContabil?.servicoContabil,
          ),
        ),
      )
      .subscribe((servicoContabils: IServicoContabil[]) => (this.servicoContabilsSharedCollection = servicoContabils));

    this.anexoRequeridoService
      .query()
      .pipe(map((res: HttpResponse<IAnexoRequerido[]>) => res.body ?? []))
      .pipe(
        map((anexoRequeridos: IAnexoRequerido[]) =>
          this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
            anexoRequeridos,
            this.anexoRequeridoServicoContabil?.anexoRequerido,
          ),
        ),
      )
      .subscribe((anexoRequeridos: IAnexoRequerido[]) => (this.anexoRequeridosSharedCollection = anexoRequeridos));
  }
}
