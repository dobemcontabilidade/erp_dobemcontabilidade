import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from 'app/entities/grupo-acesso-empresa/service/grupo-acesso-empresa.service';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { GrupoAcessoUsuarioEmpresaService } from '../service/grupo-acesso-usuario-empresa.service';
import { IGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';
import { GrupoAcessoUsuarioEmpresaFormService, GrupoAcessoUsuarioEmpresaFormGroup } from './grupo-acesso-usuario-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-usuario-empresa-update',
  templateUrl: './grupo-acesso-usuario-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GrupoAcessoUsuarioEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa | null = null;

  grupoAcessoEmpresasSharedCollection: IGrupoAcessoEmpresa[] = [];
  usuarioEmpresasSharedCollection: IUsuarioEmpresa[] = [];

  protected grupoAcessoUsuarioEmpresaService = inject(GrupoAcessoUsuarioEmpresaService);
  protected grupoAcessoUsuarioEmpresaFormService = inject(GrupoAcessoUsuarioEmpresaFormService);
  protected grupoAcessoEmpresaService = inject(GrupoAcessoEmpresaService);
  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GrupoAcessoUsuarioEmpresaFormGroup = this.grupoAcessoUsuarioEmpresaFormService.createGrupoAcessoUsuarioEmpresaFormGroup();

  compareGrupoAcessoEmpresa = (o1: IGrupoAcessoEmpresa | null, o2: IGrupoAcessoEmpresa | null): boolean =>
    this.grupoAcessoEmpresaService.compareGrupoAcessoEmpresa(o1, o2);

  compareUsuarioEmpresa = (o1: IUsuarioEmpresa | null, o2: IUsuarioEmpresa | null): boolean =>
    this.usuarioEmpresaService.compareUsuarioEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoAcessoUsuarioEmpresa }) => {
      this.grupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresa;
      if (grupoAcessoUsuarioEmpresa) {
        this.updateForm(grupoAcessoUsuarioEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoAcessoUsuarioEmpresa = this.grupoAcessoUsuarioEmpresaFormService.getGrupoAcessoUsuarioEmpresa(this.editForm);
    if (grupoAcessoUsuarioEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.grupoAcessoUsuarioEmpresaService.update(grupoAcessoUsuarioEmpresa));
    } else {
      this.subscribeToSaveResponse(this.grupoAcessoUsuarioEmpresaService.create(grupoAcessoUsuarioEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoAcessoUsuarioEmpresa>>): void {
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

  protected updateForm(grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa): void {
    this.grupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresa;
    this.grupoAcessoUsuarioEmpresaFormService.resetForm(this.editForm, grupoAcessoUsuarioEmpresa);

    this.grupoAcessoEmpresasSharedCollection =
      this.grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing<IGrupoAcessoEmpresa>(
        this.grupoAcessoEmpresasSharedCollection,
        grupoAcessoUsuarioEmpresa.grupoAcessoEmpresa,
      );
    this.usuarioEmpresasSharedCollection = this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
      this.usuarioEmpresasSharedCollection,
      grupoAcessoUsuarioEmpresa.usuarioEmpresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.grupoAcessoEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IGrupoAcessoEmpresa[]>) => res.body ?? []))
      .pipe(
        map((grupoAcessoEmpresas: IGrupoAcessoEmpresa[]) =>
          this.grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing<IGrupoAcessoEmpresa>(
            grupoAcessoEmpresas,
            this.grupoAcessoUsuarioEmpresa?.grupoAcessoEmpresa,
          ),
        ),
      )
      .subscribe((grupoAcessoEmpresas: IGrupoAcessoEmpresa[]) => (this.grupoAcessoEmpresasSharedCollection = grupoAcessoEmpresas));

    this.usuarioEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IUsuarioEmpresa[]>) => res.body ?? []))
      .pipe(
        map((usuarioEmpresas: IUsuarioEmpresa[]) =>
          this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
            usuarioEmpresas,
            this.grupoAcessoUsuarioEmpresa?.usuarioEmpresa,
          ),
        ),
      )
      .subscribe((usuarioEmpresas: IUsuarioEmpresa[]) => (this.usuarioEmpresasSharedCollection = usuarioEmpresas));
  }
}
