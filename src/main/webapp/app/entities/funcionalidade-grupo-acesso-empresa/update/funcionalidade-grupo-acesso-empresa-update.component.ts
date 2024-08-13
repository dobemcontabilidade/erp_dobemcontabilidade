import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionalidade } from 'app/entities/funcionalidade/funcionalidade.model';
import { FuncionalidadeService } from 'app/entities/funcionalidade/service/funcionalidade.service';
import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from 'app/entities/grupo-acesso-empresa/service/grupo-acesso-empresa.service';
import { IPermisao } from 'app/entities/permisao/permisao.model';
import { PermisaoService } from 'app/entities/permisao/service/permisao.service';
import { FuncionalidadeGrupoAcessoEmpresaService } from '../service/funcionalidade-grupo-acesso-empresa.service';
import { IFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';
import {
  FuncionalidadeGrupoAcessoEmpresaFormService,
  FuncionalidadeGrupoAcessoEmpresaFormGroup,
} from './funcionalidade-grupo-acesso-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-funcionalidade-grupo-acesso-empresa-update',
  templateUrl: './funcionalidade-grupo-acesso-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuncionalidadeGrupoAcessoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa | null = null;

  funcionalidadesSharedCollection: IFuncionalidade[] = [];
  grupoAcessoEmpresasSharedCollection: IGrupoAcessoEmpresa[] = [];
  permisaosSharedCollection: IPermisao[] = [];

  protected funcionalidadeGrupoAcessoEmpresaService = inject(FuncionalidadeGrupoAcessoEmpresaService);
  protected funcionalidadeGrupoAcessoEmpresaFormService = inject(FuncionalidadeGrupoAcessoEmpresaFormService);
  protected funcionalidadeService = inject(FuncionalidadeService);
  protected grupoAcessoEmpresaService = inject(GrupoAcessoEmpresaService);
  protected permisaoService = inject(PermisaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuncionalidadeGrupoAcessoEmpresaFormGroup =
    this.funcionalidadeGrupoAcessoEmpresaFormService.createFuncionalidadeGrupoAcessoEmpresaFormGroup();

  compareFuncionalidade = (o1: IFuncionalidade | null, o2: IFuncionalidade | null): boolean =>
    this.funcionalidadeService.compareFuncionalidade(o1, o2);

  compareGrupoAcessoEmpresa = (o1: IGrupoAcessoEmpresa | null, o2: IGrupoAcessoEmpresa | null): boolean =>
    this.grupoAcessoEmpresaService.compareGrupoAcessoEmpresa(o1, o2);

  comparePermisao = (o1: IPermisao | null, o2: IPermisao | null): boolean => this.permisaoService.comparePermisao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcionalidadeGrupoAcessoEmpresa }) => {
      this.funcionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresa;
      if (funcionalidadeGrupoAcessoEmpresa) {
        this.updateForm(funcionalidadeGrupoAcessoEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcionalidadeGrupoAcessoEmpresa = this.funcionalidadeGrupoAcessoEmpresaFormService.getFuncionalidadeGrupoAcessoEmpresa(
      this.editForm,
    );
    if (funcionalidadeGrupoAcessoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.funcionalidadeGrupoAcessoEmpresaService.update(funcionalidadeGrupoAcessoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.funcionalidadeGrupoAcessoEmpresaService.create(funcionalidadeGrupoAcessoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionalidadeGrupoAcessoEmpresa>>): void {
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

  protected updateForm(funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa): void {
    this.funcionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresa;
    this.funcionalidadeGrupoAcessoEmpresaFormService.resetForm(this.editForm, funcionalidadeGrupoAcessoEmpresa);

    this.funcionalidadesSharedCollection = this.funcionalidadeService.addFuncionalidadeToCollectionIfMissing<IFuncionalidade>(
      this.funcionalidadesSharedCollection,
      funcionalidadeGrupoAcessoEmpresa.funcionalidade,
    );
    this.grupoAcessoEmpresasSharedCollection =
      this.grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing<IGrupoAcessoEmpresa>(
        this.grupoAcessoEmpresasSharedCollection,
        funcionalidadeGrupoAcessoEmpresa.grupoAcessoEmpresa,
      );
    this.permisaosSharedCollection = this.permisaoService.addPermisaoToCollectionIfMissing<IPermisao>(
      this.permisaosSharedCollection,
      funcionalidadeGrupoAcessoEmpresa.permisao,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionalidadeService
      .query()
      .pipe(map((res: HttpResponse<IFuncionalidade[]>) => res.body ?? []))
      .pipe(
        map((funcionalidades: IFuncionalidade[]) =>
          this.funcionalidadeService.addFuncionalidadeToCollectionIfMissing<IFuncionalidade>(
            funcionalidades,
            this.funcionalidadeGrupoAcessoEmpresa?.funcionalidade,
          ),
        ),
      )
      .subscribe((funcionalidades: IFuncionalidade[]) => (this.funcionalidadesSharedCollection = funcionalidades));

    this.grupoAcessoEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IGrupoAcessoEmpresa[]>) => res.body ?? []))
      .pipe(
        map((grupoAcessoEmpresas: IGrupoAcessoEmpresa[]) =>
          this.grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing<IGrupoAcessoEmpresa>(
            grupoAcessoEmpresas,
            this.funcionalidadeGrupoAcessoEmpresa?.grupoAcessoEmpresa,
          ),
        ),
      )
      .subscribe((grupoAcessoEmpresas: IGrupoAcessoEmpresa[]) => (this.grupoAcessoEmpresasSharedCollection = grupoAcessoEmpresas));

    this.permisaoService
      .query()
      .pipe(map((res: HttpResponse<IPermisao[]>) => res.body ?? []))
      .pipe(
        map((permisaos: IPermisao[]) =>
          this.permisaoService.addPermisaoToCollectionIfMissing<IPermisao>(permisaos, this.funcionalidadeGrupoAcessoEmpresa?.permisao),
        ),
      )
      .subscribe((permisaos: IPermisao[]) => (this.permisaosSharedCollection = permisaos));
  }
}
