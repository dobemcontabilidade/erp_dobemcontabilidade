import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { IServicoContabilAssinaturaEmpresa } from 'app/entities/servico-contabil-assinatura-empresa/servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from 'app/entities/servico-contabil-assinatura-empresa/service/servico-contabil-assinatura-empresa.service';
import { AnexoServicoContabilEmpresaService } from '../service/anexo-servico-contabil-empresa.service';
import { IAnexoServicoContabilEmpresa } from '../anexo-servico-contabil-empresa.model';
import {
  AnexoServicoContabilEmpresaFormService,
  AnexoServicoContabilEmpresaFormGroup,
} from './anexo-servico-contabil-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-servico-contabil-empresa-update',
  templateUrl: './anexo-servico-contabil-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoServicoContabilEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa | null = null;

  anexoRequeridosSharedCollection: IAnexoRequerido[] = [];
  servicoContabilAssinaturaEmpresasSharedCollection: IServicoContabilAssinaturaEmpresa[] = [];

  protected anexoServicoContabilEmpresaService = inject(AnexoServicoContabilEmpresaService);
  protected anexoServicoContabilEmpresaFormService = inject(AnexoServicoContabilEmpresaFormService);
  protected anexoRequeridoService = inject(AnexoRequeridoService);
  protected servicoContabilAssinaturaEmpresaService = inject(ServicoContabilAssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoServicoContabilEmpresaFormGroup = this.anexoServicoContabilEmpresaFormService.createAnexoServicoContabilEmpresaFormGroup();

  compareAnexoRequerido = (o1: IAnexoRequerido | null, o2: IAnexoRequerido | null): boolean =>
    this.anexoRequeridoService.compareAnexoRequerido(o1, o2);

  compareServicoContabilAssinaturaEmpresa = (
    o1: IServicoContabilAssinaturaEmpresa | null,
    o2: IServicoContabilAssinaturaEmpresa | null,
  ): boolean => this.servicoContabilAssinaturaEmpresaService.compareServicoContabilAssinaturaEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoServicoContabilEmpresa }) => {
      this.anexoServicoContabilEmpresa = anexoServicoContabilEmpresa;
      if (anexoServicoContabilEmpresa) {
        this.updateForm(anexoServicoContabilEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexoServicoContabilEmpresa = this.anexoServicoContabilEmpresaFormService.getAnexoServicoContabilEmpresa(this.editForm);
    if (anexoServicoContabilEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.anexoServicoContabilEmpresaService.update(anexoServicoContabilEmpresa));
    } else {
      this.subscribeToSaveResponse(this.anexoServicoContabilEmpresaService.create(anexoServicoContabilEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoServicoContabilEmpresa>>): void {
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

  protected updateForm(anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa): void {
    this.anexoServicoContabilEmpresa = anexoServicoContabilEmpresa;
    this.anexoServicoContabilEmpresaFormService.resetForm(this.editForm, anexoServicoContabilEmpresa);

    this.anexoRequeridosSharedCollection = this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
      this.anexoRequeridosSharedCollection,
      anexoServicoContabilEmpresa.anexoRequerido,
    );
    this.servicoContabilAssinaturaEmpresasSharedCollection =
      this.servicoContabilAssinaturaEmpresaService.addServicoContabilAssinaturaEmpresaToCollectionIfMissing<IServicoContabilAssinaturaEmpresa>(
        this.servicoContabilAssinaturaEmpresasSharedCollection,
        anexoServicoContabilEmpresa.servicoContabilAssinaturaEmpresa,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.anexoRequeridoService
      .query()
      .pipe(map((res: HttpResponse<IAnexoRequerido[]>) => res.body ?? []))
      .pipe(
        map((anexoRequeridos: IAnexoRequerido[]) =>
          this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
            anexoRequeridos,
            this.anexoServicoContabilEmpresa?.anexoRequerido,
          ),
        ),
      )
      .subscribe((anexoRequeridos: IAnexoRequerido[]) => (this.anexoRequeridosSharedCollection = anexoRequeridos));

    this.servicoContabilAssinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabilAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((servicoContabilAssinaturaEmpresas: IServicoContabilAssinaturaEmpresa[]) =>
          this.servicoContabilAssinaturaEmpresaService.addServicoContabilAssinaturaEmpresaToCollectionIfMissing<IServicoContabilAssinaturaEmpresa>(
            servicoContabilAssinaturaEmpresas,
            this.anexoServicoContabilEmpresa?.servicoContabilAssinaturaEmpresa,
          ),
        ),
      )
      .subscribe(
        (servicoContabilAssinaturaEmpresas: IServicoContabilAssinaturaEmpresa[]) =>
          (this.servicoContabilAssinaturaEmpresasSharedCollection = servicoContabilAssinaturaEmpresas),
      );
  }
}
