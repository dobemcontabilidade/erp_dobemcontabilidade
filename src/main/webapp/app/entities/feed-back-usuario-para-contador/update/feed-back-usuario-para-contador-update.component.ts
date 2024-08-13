import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { UsuarioContadorService } from 'app/entities/usuario-contador/service/usuario-contador.service';
import { ICriterioAvaliacaoAtor } from 'app/entities/criterio-avaliacao-ator/criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from 'app/entities/criterio-avaliacao-ator/service/criterio-avaliacao-ator.service';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { FeedBackUsuarioParaContadorService } from '../service/feed-back-usuario-para-contador.service';
import { IFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';
import {
  FeedBackUsuarioParaContadorFormService,
  FeedBackUsuarioParaContadorFormGroup,
} from './feed-back-usuario-para-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-feed-back-usuario-para-contador-update',
  templateUrl: './feed-back-usuario-para-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FeedBackUsuarioParaContadorUpdateComponent implements OnInit {
  isSaving = false;
  feedBackUsuarioParaContador: IFeedBackUsuarioParaContador | null = null;

  usuarioEmpresasSharedCollection: IUsuarioEmpresa[] = [];
  usuarioContadorsSharedCollection: IUsuarioContador[] = [];
  criterioAvaliacaoAtorsSharedCollection: ICriterioAvaliacaoAtor[] = [];
  ordemServicosSharedCollection: IOrdemServico[] = [];

  protected feedBackUsuarioParaContadorService = inject(FeedBackUsuarioParaContadorService);
  protected feedBackUsuarioParaContadorFormService = inject(FeedBackUsuarioParaContadorFormService);
  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected usuarioContadorService = inject(UsuarioContadorService);
  protected criterioAvaliacaoAtorService = inject(CriterioAvaliacaoAtorService);
  protected ordemServicoService = inject(OrdemServicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FeedBackUsuarioParaContadorFormGroup = this.feedBackUsuarioParaContadorFormService.createFeedBackUsuarioParaContadorFormGroup();

  compareUsuarioEmpresa = (o1: IUsuarioEmpresa | null, o2: IUsuarioEmpresa | null): boolean =>
    this.usuarioEmpresaService.compareUsuarioEmpresa(o1, o2);

  compareUsuarioContador = (o1: IUsuarioContador | null, o2: IUsuarioContador | null): boolean =>
    this.usuarioContadorService.compareUsuarioContador(o1, o2);

  compareCriterioAvaliacaoAtor = (o1: ICriterioAvaliacaoAtor | null, o2: ICriterioAvaliacaoAtor | null): boolean =>
    this.criterioAvaliacaoAtorService.compareCriterioAvaliacaoAtor(o1, o2);

  compareOrdemServico = (o1: IOrdemServico | null, o2: IOrdemServico | null): boolean =>
    this.ordemServicoService.compareOrdemServico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedBackUsuarioParaContador }) => {
      this.feedBackUsuarioParaContador = feedBackUsuarioParaContador;
      if (feedBackUsuarioParaContador) {
        this.updateForm(feedBackUsuarioParaContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feedBackUsuarioParaContador = this.feedBackUsuarioParaContadorFormService.getFeedBackUsuarioParaContador(this.editForm);
    if (feedBackUsuarioParaContador.id !== null) {
      this.subscribeToSaveResponse(this.feedBackUsuarioParaContadorService.update(feedBackUsuarioParaContador));
    } else {
      this.subscribeToSaveResponse(this.feedBackUsuarioParaContadorService.create(feedBackUsuarioParaContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedBackUsuarioParaContador>>): void {
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

  protected updateForm(feedBackUsuarioParaContador: IFeedBackUsuarioParaContador): void {
    this.feedBackUsuarioParaContador = feedBackUsuarioParaContador;
    this.feedBackUsuarioParaContadorFormService.resetForm(this.editForm, feedBackUsuarioParaContador);

    this.usuarioEmpresasSharedCollection = this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
      this.usuarioEmpresasSharedCollection,
      feedBackUsuarioParaContador.usuarioEmpresa,
    );
    this.usuarioContadorsSharedCollection = this.usuarioContadorService.addUsuarioContadorToCollectionIfMissing<IUsuarioContador>(
      this.usuarioContadorsSharedCollection,
      feedBackUsuarioParaContador.usuarioContador,
    );
    this.criterioAvaliacaoAtorsSharedCollection =
      this.criterioAvaliacaoAtorService.addCriterioAvaliacaoAtorToCollectionIfMissing<ICriterioAvaliacaoAtor>(
        this.criterioAvaliacaoAtorsSharedCollection,
        feedBackUsuarioParaContador.criterioAvaliacaoAtor,
      );
    this.ordemServicosSharedCollection = this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
      this.ordemServicosSharedCollection,
      feedBackUsuarioParaContador.ordemServico,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.usuarioEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IUsuarioEmpresa[]>) => res.body ?? []))
      .pipe(
        map((usuarioEmpresas: IUsuarioEmpresa[]) =>
          this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
            usuarioEmpresas,
            this.feedBackUsuarioParaContador?.usuarioEmpresa,
          ),
        ),
      )
      .subscribe((usuarioEmpresas: IUsuarioEmpresa[]) => (this.usuarioEmpresasSharedCollection = usuarioEmpresas));

    this.usuarioContadorService
      .query()
      .pipe(map((res: HttpResponse<IUsuarioContador[]>) => res.body ?? []))
      .pipe(
        map((usuarioContadors: IUsuarioContador[]) =>
          this.usuarioContadorService.addUsuarioContadorToCollectionIfMissing<IUsuarioContador>(
            usuarioContadors,
            this.feedBackUsuarioParaContador?.usuarioContador,
          ),
        ),
      )
      .subscribe((usuarioContadors: IUsuarioContador[]) => (this.usuarioContadorsSharedCollection = usuarioContadors));

    this.criterioAvaliacaoAtorService
      .query()
      .pipe(map((res: HttpResponse<ICriterioAvaliacaoAtor[]>) => res.body ?? []))
      .pipe(
        map((criterioAvaliacaoAtors: ICriterioAvaliacaoAtor[]) =>
          this.criterioAvaliacaoAtorService.addCriterioAvaliacaoAtorToCollectionIfMissing<ICriterioAvaliacaoAtor>(
            criterioAvaliacaoAtors,
            this.feedBackUsuarioParaContador?.criterioAvaliacaoAtor,
          ),
        ),
      )
      .subscribe(
        (criterioAvaliacaoAtors: ICriterioAvaliacaoAtor[]) => (this.criterioAvaliacaoAtorsSharedCollection = criterioAvaliacaoAtors),
      );

    this.ordemServicoService
      .query()
      .pipe(map((res: HttpResponse<IOrdemServico[]>) => res.body ?? []))
      .pipe(
        map((ordemServicos: IOrdemServico[]) =>
          this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
            ordemServicos,
            this.feedBackUsuarioParaContador?.ordemServico,
          ),
        ),
      )
      .subscribe((ordemServicos: IOrdemServico[]) => (this.ordemServicosSharedCollection = ordemServicos));
  }
}
