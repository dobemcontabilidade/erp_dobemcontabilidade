import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { ServicoContabilAssinaturaEmpresaService } from '../service/servico-contabil-assinatura-empresa.service';
import { IServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';
import {
  ServicoContabilAssinaturaEmpresaFormService,
  ServicoContabilAssinaturaEmpresaFormGroup,
} from './servico-contabil-assinatura-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-assinatura-empresa-update',
  templateUrl: './servico-contabil-assinatura-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServicoContabilAssinaturaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa | null = null;

  servicoContabilsSharedCollection: IServicoContabil[] = [];
  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];

  protected servicoContabilAssinaturaEmpresaService = inject(ServicoContabilAssinaturaEmpresaService);
  protected servicoContabilAssinaturaEmpresaFormService = inject(ServicoContabilAssinaturaEmpresaFormService);
  protected servicoContabilService = inject(ServicoContabilService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServicoContabilAssinaturaEmpresaFormGroup =
    this.servicoContabilAssinaturaEmpresaFormService.createServicoContabilAssinaturaEmpresaFormGroup();

  compareServicoContabil = (o1: IServicoContabil | null, o2: IServicoContabil | null): boolean =>
    this.servicoContabilService.compareServicoContabil(o1, o2);

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicoContabilAssinaturaEmpresa }) => {
      this.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;
      if (servicoContabilAssinaturaEmpresa) {
        this.updateForm(servicoContabilAssinaturaEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicoContabilAssinaturaEmpresa = this.servicoContabilAssinaturaEmpresaFormService.getServicoContabilAssinaturaEmpresa(
      this.editForm,
    );
    if (servicoContabilAssinaturaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.servicoContabilAssinaturaEmpresaService.update(servicoContabilAssinaturaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.servicoContabilAssinaturaEmpresaService.create(servicoContabilAssinaturaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicoContabilAssinaturaEmpresa>>): void {
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

  protected updateForm(servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa): void {
    this.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;
    this.servicoContabilAssinaturaEmpresaFormService.resetForm(this.editForm, servicoContabilAssinaturaEmpresa);

    this.servicoContabilsSharedCollection = this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
      this.servicoContabilsSharedCollection,
      servicoContabilAssinaturaEmpresa.servicoContabil,
    );
    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      servicoContabilAssinaturaEmpresa.assinaturaEmpresa,
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
            this.servicoContabilAssinaturaEmpresa?.servicoContabil,
          ),
        ),
      )
      .subscribe((servicoContabils: IServicoContabil[]) => (this.servicoContabilsSharedCollection = servicoContabils));

    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.servicoContabilAssinaturaEmpresa?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));
  }
}
