import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { UsuarioContadorService } from 'app/entities/usuario-contador/service/usuario-contador.service';
import { IGrupoAcessoPadrao } from 'app/entities/grupo-acesso-padrao/grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from 'app/entities/grupo-acesso-padrao/service/grupo-acesso-padrao.service';
import { GrupoAcessoUsuarioContadorService } from '../service/grupo-acesso-usuario-contador.service';
import { IGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';
import { GrupoAcessoUsuarioContadorFormService, GrupoAcessoUsuarioContadorFormGroup } from './grupo-acesso-usuario-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-usuario-contador-update',
  templateUrl: './grupo-acesso-usuario-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GrupoAcessoUsuarioContadorUpdateComponent implements OnInit {
  isSaving = false;
  grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador | null = null;

  usuarioContadorsSharedCollection: IUsuarioContador[] = [];
  grupoAcessoPadraosSharedCollection: IGrupoAcessoPadrao[] = [];

  protected grupoAcessoUsuarioContadorService = inject(GrupoAcessoUsuarioContadorService);
  protected grupoAcessoUsuarioContadorFormService = inject(GrupoAcessoUsuarioContadorFormService);
  protected usuarioContadorService = inject(UsuarioContadorService);
  protected grupoAcessoPadraoService = inject(GrupoAcessoPadraoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GrupoAcessoUsuarioContadorFormGroup = this.grupoAcessoUsuarioContadorFormService.createGrupoAcessoUsuarioContadorFormGroup();

  compareUsuarioContador = (o1: IUsuarioContador | null, o2: IUsuarioContador | null): boolean =>
    this.usuarioContadorService.compareUsuarioContador(o1, o2);

  compareGrupoAcessoPadrao = (o1: IGrupoAcessoPadrao | null, o2: IGrupoAcessoPadrao | null): boolean =>
    this.grupoAcessoPadraoService.compareGrupoAcessoPadrao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoAcessoUsuarioContador }) => {
      this.grupoAcessoUsuarioContador = grupoAcessoUsuarioContador;
      if (grupoAcessoUsuarioContador) {
        this.updateForm(grupoAcessoUsuarioContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoAcessoUsuarioContador = this.grupoAcessoUsuarioContadorFormService.getGrupoAcessoUsuarioContador(this.editForm);
    if (grupoAcessoUsuarioContador.id !== null) {
      this.subscribeToSaveResponse(this.grupoAcessoUsuarioContadorService.update(grupoAcessoUsuarioContador));
    } else {
      this.subscribeToSaveResponse(this.grupoAcessoUsuarioContadorService.create(grupoAcessoUsuarioContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoAcessoUsuarioContador>>): void {
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

  protected updateForm(grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador): void {
    this.grupoAcessoUsuarioContador = grupoAcessoUsuarioContador;
    this.grupoAcessoUsuarioContadorFormService.resetForm(this.editForm, grupoAcessoUsuarioContador);

    this.usuarioContadorsSharedCollection = this.usuarioContadorService.addUsuarioContadorToCollectionIfMissing<IUsuarioContador>(
      this.usuarioContadorsSharedCollection,
      grupoAcessoUsuarioContador.usuarioContador,
    );
    this.grupoAcessoPadraosSharedCollection = this.grupoAcessoPadraoService.addGrupoAcessoPadraoToCollectionIfMissing<IGrupoAcessoPadrao>(
      this.grupoAcessoPadraosSharedCollection,
      grupoAcessoUsuarioContador.grupoAcessoPadrao,
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
            this.grupoAcessoUsuarioContador?.usuarioContador,
          ),
        ),
      )
      .subscribe((usuarioContadors: IUsuarioContador[]) => (this.usuarioContadorsSharedCollection = usuarioContadors));

    this.grupoAcessoPadraoService
      .query()
      .pipe(map((res: HttpResponse<IGrupoAcessoPadrao[]>) => res.body ?? []))
      .pipe(
        map((grupoAcessoPadraos: IGrupoAcessoPadrao[]) =>
          this.grupoAcessoPadraoService.addGrupoAcessoPadraoToCollectionIfMissing<IGrupoAcessoPadrao>(
            grupoAcessoPadraos,
            this.grupoAcessoUsuarioContador?.grupoAcessoPadrao,
          ),
        ),
      )
      .subscribe((grupoAcessoPadraos: IGrupoAcessoPadrao[]) => (this.grupoAcessoPadraosSharedCollection = grupoAcessoPadraos));
  }
}
