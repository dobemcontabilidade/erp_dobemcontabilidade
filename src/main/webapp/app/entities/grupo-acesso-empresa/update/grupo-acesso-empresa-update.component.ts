import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IGrupoAcessoEmpresa } from '../grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from '../service/grupo-acesso-empresa.service';
import { GrupoAcessoEmpresaFormService, GrupoAcessoEmpresaFormGroup } from './grupo-acesso-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-empresa-update',
  templateUrl: './grupo-acesso-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GrupoAcessoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  grupoAcessoEmpresa: IGrupoAcessoEmpresa | null = null;

  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];

  protected grupoAcessoEmpresaService = inject(GrupoAcessoEmpresaService);
  protected grupoAcessoEmpresaFormService = inject(GrupoAcessoEmpresaFormService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GrupoAcessoEmpresaFormGroup = this.grupoAcessoEmpresaFormService.createGrupoAcessoEmpresaFormGroup();

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoAcessoEmpresa }) => {
      this.grupoAcessoEmpresa = grupoAcessoEmpresa;
      if (grupoAcessoEmpresa) {
        this.updateForm(grupoAcessoEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoAcessoEmpresa = this.grupoAcessoEmpresaFormService.getGrupoAcessoEmpresa(this.editForm);
    if (grupoAcessoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.grupoAcessoEmpresaService.update(grupoAcessoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.grupoAcessoEmpresaService.create(grupoAcessoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoAcessoEmpresa>>): void {
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

  protected updateForm(grupoAcessoEmpresa: IGrupoAcessoEmpresa): void {
    this.grupoAcessoEmpresa = grupoAcessoEmpresa;
    this.grupoAcessoEmpresaFormService.resetForm(this.editForm, grupoAcessoEmpresa);

    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      grupoAcessoEmpresa.assinaturaEmpresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.grupoAcessoEmpresa?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));
  }
}
