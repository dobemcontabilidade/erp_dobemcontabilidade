import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { ServicoContabilEmpresaModeloService } from '../service/servico-contabil-empresa-modelo.service';
import { IServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';
import {
  ServicoContabilEmpresaModeloFormService,
  ServicoContabilEmpresaModeloFormGroup,
} from './servico-contabil-empresa-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-empresa-modelo-update',
  templateUrl: './servico-contabil-empresa-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServicoContabilEmpresaModeloUpdateComponent implements OnInit {
  isSaving = false;
  servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo | null = null;

  empresaModelosSharedCollection: IEmpresaModelo[] = [];
  servicoContabilsSharedCollection: IServicoContabil[] = [];

  protected servicoContabilEmpresaModeloService = inject(ServicoContabilEmpresaModeloService);
  protected servicoContabilEmpresaModeloFormService = inject(ServicoContabilEmpresaModeloFormService);
  protected empresaModeloService = inject(EmpresaModeloService);
  protected servicoContabilService = inject(ServicoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServicoContabilEmpresaModeloFormGroup =
    this.servicoContabilEmpresaModeloFormService.createServicoContabilEmpresaModeloFormGroup();

  compareEmpresaModelo = (o1: IEmpresaModelo | null, o2: IEmpresaModelo | null): boolean =>
    this.empresaModeloService.compareEmpresaModelo(o1, o2);

  compareServicoContabil = (o1: IServicoContabil | null, o2: IServicoContabil | null): boolean =>
    this.servicoContabilService.compareServicoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicoContabilEmpresaModelo }) => {
      this.servicoContabilEmpresaModelo = servicoContabilEmpresaModelo;
      if (servicoContabilEmpresaModelo) {
        this.updateForm(servicoContabilEmpresaModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicoContabilEmpresaModelo = this.servicoContabilEmpresaModeloFormService.getServicoContabilEmpresaModelo(this.editForm);
    if (servicoContabilEmpresaModelo.id !== null) {
      this.subscribeToSaveResponse(this.servicoContabilEmpresaModeloService.update(servicoContabilEmpresaModelo));
    } else {
      this.subscribeToSaveResponse(this.servicoContabilEmpresaModeloService.create(servicoContabilEmpresaModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicoContabilEmpresaModelo>>): void {
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

  protected updateForm(servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo): void {
    this.servicoContabilEmpresaModelo = servicoContabilEmpresaModelo;
    this.servicoContabilEmpresaModeloFormService.resetForm(this.editForm, servicoContabilEmpresaModelo);

    this.empresaModelosSharedCollection = this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
      this.empresaModelosSharedCollection,
      servicoContabilEmpresaModelo.empresaModelo,
    );
    this.servicoContabilsSharedCollection = this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
      this.servicoContabilsSharedCollection,
      servicoContabilEmpresaModelo.servicoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaModeloService
      .query()
      .pipe(map((res: HttpResponse<IEmpresaModelo[]>) => res.body ?? []))
      .pipe(
        map((empresaModelos: IEmpresaModelo[]) =>
          this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
            empresaModelos,
            this.servicoContabilEmpresaModelo?.empresaModelo,
          ),
        ),
      )
      .subscribe((empresaModelos: IEmpresaModelo[]) => (this.empresaModelosSharedCollection = empresaModelos));

    this.servicoContabilService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabil[]>) => res.body ?? []))
      .pipe(
        map((servicoContabils: IServicoContabil[]) =>
          this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
            servicoContabils,
            this.servicoContabilEmpresaModelo?.servicoContabil,
          ),
        ),
      )
      .subscribe((servicoContabils: IServicoContabil[]) => (this.servicoContabilsSharedCollection = servicoContabils));
  }
}
