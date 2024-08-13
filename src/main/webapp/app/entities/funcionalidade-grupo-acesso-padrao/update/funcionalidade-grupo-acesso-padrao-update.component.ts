import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionalidade } from 'app/entities/funcionalidade/funcionalidade.model';
import { FuncionalidadeService } from 'app/entities/funcionalidade/service/funcionalidade.service';
import { IGrupoAcessoPadrao } from 'app/entities/grupo-acesso-padrao/grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from 'app/entities/grupo-acesso-padrao/service/grupo-acesso-padrao.service';
import { IPermisao } from 'app/entities/permisao/permisao.model';
import { PermisaoService } from 'app/entities/permisao/service/permisao.service';
import { FuncionalidadeGrupoAcessoPadraoService } from '../service/funcionalidade-grupo-acesso-padrao.service';
import { IFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';
import {
  FuncionalidadeGrupoAcessoPadraoFormService,
  FuncionalidadeGrupoAcessoPadraoFormGroup,
} from './funcionalidade-grupo-acesso-padrao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-funcionalidade-grupo-acesso-padrao-update',
  templateUrl: './funcionalidade-grupo-acesso-padrao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuncionalidadeGrupoAcessoPadraoUpdateComponent implements OnInit {
  isSaving = false;
  funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao | null = null;

  funcionalidadesSharedCollection: IFuncionalidade[] = [];
  grupoAcessoPadraosSharedCollection: IGrupoAcessoPadrao[] = [];
  permisaosSharedCollection: IPermisao[] = [];

  protected funcionalidadeGrupoAcessoPadraoService = inject(FuncionalidadeGrupoAcessoPadraoService);
  protected funcionalidadeGrupoAcessoPadraoFormService = inject(FuncionalidadeGrupoAcessoPadraoFormService);
  protected funcionalidadeService = inject(FuncionalidadeService);
  protected grupoAcessoPadraoService = inject(GrupoAcessoPadraoService);
  protected permisaoService = inject(PermisaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuncionalidadeGrupoAcessoPadraoFormGroup =
    this.funcionalidadeGrupoAcessoPadraoFormService.createFuncionalidadeGrupoAcessoPadraoFormGroup();

  compareFuncionalidade = (o1: IFuncionalidade | null, o2: IFuncionalidade | null): boolean =>
    this.funcionalidadeService.compareFuncionalidade(o1, o2);

  compareGrupoAcessoPadrao = (o1: IGrupoAcessoPadrao | null, o2: IGrupoAcessoPadrao | null): boolean =>
    this.grupoAcessoPadraoService.compareGrupoAcessoPadrao(o1, o2);

  comparePermisao = (o1: IPermisao | null, o2: IPermisao | null): boolean => this.permisaoService.comparePermisao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcionalidadeGrupoAcessoPadrao }) => {
      this.funcionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadrao;
      if (funcionalidadeGrupoAcessoPadrao) {
        this.updateForm(funcionalidadeGrupoAcessoPadrao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcionalidadeGrupoAcessoPadrao = this.funcionalidadeGrupoAcessoPadraoFormService.getFuncionalidadeGrupoAcessoPadrao(
      this.editForm,
    );
    if (funcionalidadeGrupoAcessoPadrao.id !== null) {
      this.subscribeToSaveResponse(this.funcionalidadeGrupoAcessoPadraoService.update(funcionalidadeGrupoAcessoPadrao));
    } else {
      this.subscribeToSaveResponse(this.funcionalidadeGrupoAcessoPadraoService.create(funcionalidadeGrupoAcessoPadrao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionalidadeGrupoAcessoPadrao>>): void {
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

  protected updateForm(funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao): void {
    this.funcionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadrao;
    this.funcionalidadeGrupoAcessoPadraoFormService.resetForm(this.editForm, funcionalidadeGrupoAcessoPadrao);

    this.funcionalidadesSharedCollection = this.funcionalidadeService.addFuncionalidadeToCollectionIfMissing<IFuncionalidade>(
      this.funcionalidadesSharedCollection,
      funcionalidadeGrupoAcessoPadrao.funcionalidade,
    );
    this.grupoAcessoPadraosSharedCollection = this.grupoAcessoPadraoService.addGrupoAcessoPadraoToCollectionIfMissing<IGrupoAcessoPadrao>(
      this.grupoAcessoPadraosSharedCollection,
      funcionalidadeGrupoAcessoPadrao.grupoAcessoPadrao,
    );
    this.permisaosSharedCollection = this.permisaoService.addPermisaoToCollectionIfMissing<IPermisao>(
      this.permisaosSharedCollection,
      funcionalidadeGrupoAcessoPadrao.permisao,
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
            this.funcionalidadeGrupoAcessoPadrao?.funcionalidade,
          ),
        ),
      )
      .subscribe((funcionalidades: IFuncionalidade[]) => (this.funcionalidadesSharedCollection = funcionalidades));

    this.grupoAcessoPadraoService
      .query()
      .pipe(map((res: HttpResponse<IGrupoAcessoPadrao[]>) => res.body ?? []))
      .pipe(
        map((grupoAcessoPadraos: IGrupoAcessoPadrao[]) =>
          this.grupoAcessoPadraoService.addGrupoAcessoPadraoToCollectionIfMissing<IGrupoAcessoPadrao>(
            grupoAcessoPadraos,
            this.funcionalidadeGrupoAcessoPadrao?.grupoAcessoPadrao,
          ),
        ),
      )
      .subscribe((grupoAcessoPadraos: IGrupoAcessoPadrao[]) => (this.grupoAcessoPadraosSharedCollection = grupoAcessoPadraos));

    this.permisaoService
      .query()
      .pipe(map((res: HttpResponse<IPermisao[]>) => res.body ?? []))
      .pipe(
        map((permisaos: IPermisao[]) =>
          this.permisaoService.addPermisaoToCollectionIfMissing<IPermisao>(permisaos, this.funcionalidadeGrupoAcessoPadrao?.permisao),
        ),
      )
      .subscribe((permisaos: IPermisao[]) => (this.permisaosSharedCollection = permisaos));
  }
}
