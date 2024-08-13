import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { UsuarioContadorService } from 'app/entities/usuario-contador/service/usuario-contador.service';
import { IPermisao } from 'app/entities/permisao/permisao.model';
import { PermisaoService } from 'app/entities/permisao/service/permisao.service';
import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from 'app/entities/grupo-acesso-empresa/service/grupo-acesso-empresa.service';
import { GrupoAcessoEmpresaUsuarioContadorService } from '../service/grupo-acesso-empresa-usuario-contador.service';
import { IGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';
import {
  GrupoAcessoEmpresaUsuarioContadorFormService,
  GrupoAcessoEmpresaUsuarioContadorFormGroup,
} from './grupo-acesso-empresa-usuario-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-empresa-usuario-contador-update',
  templateUrl: './grupo-acesso-empresa-usuario-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GrupoAcessoEmpresaUsuarioContadorUpdateComponent implements OnInit {
  isSaving = false;
  grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador | null = null;

  usuarioContadorsSharedCollection: IUsuarioContador[] = [];
  permisaosSharedCollection: IPermisao[] = [];
  grupoAcessoEmpresasSharedCollection: IGrupoAcessoEmpresa[] = [];

  protected grupoAcessoEmpresaUsuarioContadorService = inject(GrupoAcessoEmpresaUsuarioContadorService);
  protected grupoAcessoEmpresaUsuarioContadorFormService = inject(GrupoAcessoEmpresaUsuarioContadorFormService);
  protected usuarioContadorService = inject(UsuarioContadorService);
  protected permisaoService = inject(PermisaoService);
  protected grupoAcessoEmpresaService = inject(GrupoAcessoEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GrupoAcessoEmpresaUsuarioContadorFormGroup =
    this.grupoAcessoEmpresaUsuarioContadorFormService.createGrupoAcessoEmpresaUsuarioContadorFormGroup();

  compareUsuarioContador = (o1: IUsuarioContador | null, o2: IUsuarioContador | null): boolean =>
    this.usuarioContadorService.compareUsuarioContador(o1, o2);

  comparePermisao = (o1: IPermisao | null, o2: IPermisao | null): boolean => this.permisaoService.comparePermisao(o1, o2);

  compareGrupoAcessoEmpresa = (o1: IGrupoAcessoEmpresa | null, o2: IGrupoAcessoEmpresa | null): boolean =>
    this.grupoAcessoEmpresaService.compareGrupoAcessoEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoAcessoEmpresaUsuarioContador }) => {
      this.grupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContador;
      if (grupoAcessoEmpresaUsuarioContador) {
        this.updateForm(grupoAcessoEmpresaUsuarioContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoAcessoEmpresaUsuarioContador = this.grupoAcessoEmpresaUsuarioContadorFormService.getGrupoAcessoEmpresaUsuarioContador(
      this.editForm,
    );
    if (grupoAcessoEmpresaUsuarioContador.id !== null) {
      this.subscribeToSaveResponse(this.grupoAcessoEmpresaUsuarioContadorService.update(grupoAcessoEmpresaUsuarioContador));
    } else {
      this.subscribeToSaveResponse(this.grupoAcessoEmpresaUsuarioContadorService.create(grupoAcessoEmpresaUsuarioContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoAcessoEmpresaUsuarioContador>>): void {
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

  protected updateForm(grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador): void {
    this.grupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContador;
    this.grupoAcessoEmpresaUsuarioContadorFormService.resetForm(this.editForm, grupoAcessoEmpresaUsuarioContador);

    this.usuarioContadorsSharedCollection = this.usuarioContadorService.addUsuarioContadorToCollectionIfMissing<IUsuarioContador>(
      this.usuarioContadorsSharedCollection,
      grupoAcessoEmpresaUsuarioContador.usuarioContador,
    );
    this.permisaosSharedCollection = this.permisaoService.addPermisaoToCollectionIfMissing<IPermisao>(
      this.permisaosSharedCollection,
      grupoAcessoEmpresaUsuarioContador.permisao,
    );
    this.grupoAcessoEmpresasSharedCollection =
      this.grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing<IGrupoAcessoEmpresa>(
        this.grupoAcessoEmpresasSharedCollection,
        grupoAcessoEmpresaUsuarioContador.grupoAcessoEmpresa,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.usuarioContadorService
      .query()
      .pipe(map((res: HttpResponse<IUsuarioContador[]>) => res.body ?? []))
      .pipe(
        map((usuarioContadors: IUsuarioContador[]) =>
          this.usuarioContadorService.addUsuarioContadorToCollectionIfMissing<IUsuarioContador>(
            usuarioContadors,
            this.grupoAcessoEmpresaUsuarioContador?.usuarioContador,
          ),
        ),
      )
      .subscribe((usuarioContadors: IUsuarioContador[]) => (this.usuarioContadorsSharedCollection = usuarioContadors));

    this.permisaoService
      .query()
      .pipe(map((res: HttpResponse<IPermisao[]>) => res.body ?? []))
      .pipe(
        map((permisaos: IPermisao[]) =>
          this.permisaoService.addPermisaoToCollectionIfMissing<IPermisao>(permisaos, this.grupoAcessoEmpresaUsuarioContador?.permisao),
        ),
      )
      .subscribe((permisaos: IPermisao[]) => (this.permisaosSharedCollection = permisaos));

    this.grupoAcessoEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IGrupoAcessoEmpresa[]>) => res.body ?? []))
      .pipe(
        map((grupoAcessoEmpresas: IGrupoAcessoEmpresa[]) =>
          this.grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing<IGrupoAcessoEmpresa>(
            grupoAcessoEmpresas,
            this.grupoAcessoEmpresaUsuarioContador?.grupoAcessoEmpresa,
          ),
        ),
      )
      .subscribe((grupoAcessoEmpresas: IGrupoAcessoEmpresa[]) => (this.grupoAcessoEmpresasSharedCollection = grupoAcessoEmpresas));
  }
}
