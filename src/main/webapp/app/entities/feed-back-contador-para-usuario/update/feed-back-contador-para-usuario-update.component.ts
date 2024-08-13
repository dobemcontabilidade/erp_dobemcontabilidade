import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICriterioAvaliacaoAtor } from 'app/entities/criterio-avaliacao-ator/criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from 'app/entities/criterio-avaliacao-ator/service/criterio-avaliacao-ator.service';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { FeedBackContadorParaUsuarioService } from '../service/feed-back-contador-para-usuario.service';
import { IFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';
import {
  FeedBackContadorParaUsuarioFormService,
  FeedBackContadorParaUsuarioFormGroup,
} from './feed-back-contador-para-usuario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-feed-back-contador-para-usuario-update',
  templateUrl: './feed-back-contador-para-usuario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FeedBackContadorParaUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  feedBackContadorParaUsuario: IFeedBackContadorParaUsuario | null = null;

  criterioAvaliacaoAtorsSharedCollection: ICriterioAvaliacaoAtor[] = [];
  usuarioEmpresasSharedCollection: IUsuarioEmpresa[] = [];
  contadorsSharedCollection: IContador[] = [];
  ordemServicosSharedCollection: IOrdemServico[] = [];

  protected feedBackContadorParaUsuarioService = inject(FeedBackContadorParaUsuarioService);
  protected feedBackContadorParaUsuarioFormService = inject(FeedBackContadorParaUsuarioFormService);
  protected criterioAvaliacaoAtorService = inject(CriterioAvaliacaoAtorService);
  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected contadorService = inject(ContadorService);
  protected ordemServicoService = inject(OrdemServicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FeedBackContadorParaUsuarioFormGroup = this.feedBackContadorParaUsuarioFormService.createFeedBackContadorParaUsuarioFormGroup();

  compareCriterioAvaliacaoAtor = (o1: ICriterioAvaliacaoAtor | null, o2: ICriterioAvaliacaoAtor | null): boolean =>
    this.criterioAvaliacaoAtorService.compareCriterioAvaliacaoAtor(o1, o2);

  compareUsuarioEmpresa = (o1: IUsuarioEmpresa | null, o2: IUsuarioEmpresa | null): boolean =>
    this.usuarioEmpresaService.compareUsuarioEmpresa(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  compareOrdemServico = (o1: IOrdemServico | null, o2: IOrdemServico | null): boolean =>
    this.ordemServicoService.compareOrdemServico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedBackContadorParaUsuario }) => {
      this.feedBackContadorParaUsuario = feedBackContadorParaUsuario;
      if (feedBackContadorParaUsuario) {
        this.updateForm(feedBackContadorParaUsuario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feedBackContadorParaUsuario = this.feedBackContadorParaUsuarioFormService.getFeedBackContadorParaUsuario(this.editForm);
    if (feedBackContadorParaUsuario.id !== null) {
      this.subscribeToSaveResponse(this.feedBackContadorParaUsuarioService.update(feedBackContadorParaUsuario));
    } else {
      this.subscribeToSaveResponse(this.feedBackContadorParaUsuarioService.create(feedBackContadorParaUsuario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedBackContadorParaUsuario>>): void {
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

  protected updateForm(feedBackContadorParaUsuario: IFeedBackContadorParaUsuario): void {
    this.feedBackContadorParaUsuario = feedBackContadorParaUsuario;
    this.feedBackContadorParaUsuarioFormService.resetForm(this.editForm, feedBackContadorParaUsuario);

    this.criterioAvaliacaoAtorsSharedCollection =
      this.criterioAvaliacaoAtorService.addCriterioAvaliacaoAtorToCollectionIfMissing<ICriterioAvaliacaoAtor>(
        this.criterioAvaliacaoAtorsSharedCollection,
        feedBackContadorParaUsuario.criterioAvaliacaoAtor,
      );
    this.usuarioEmpresasSharedCollection = this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
      this.usuarioEmpresasSharedCollection,
      feedBackContadorParaUsuario.usuarioEmpresa,
    );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      feedBackContadorParaUsuario.contador,
    );
    this.ordemServicosSharedCollection = this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
      this.ordemServicosSharedCollection,
      feedBackContadorParaUsuario.ordemServico,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.criterioAvaliacaoAtorService
      .query()
      .pipe(map((res: HttpResponse<ICriterioAvaliacaoAtor[]>) => res.body ?? []))
      .pipe(
        map((criterioAvaliacaoAtors: ICriterioAvaliacaoAtor[]) =>
          this.criterioAvaliacaoAtorService.addCriterioAvaliacaoAtorToCollectionIfMissing<ICriterioAvaliacaoAtor>(
            criterioAvaliacaoAtors,
            this.feedBackContadorParaUsuario?.criterioAvaliacaoAtor,
          ),
        ),
      )
      .subscribe(
        (criterioAvaliacaoAtors: ICriterioAvaliacaoAtor[]) => (this.criterioAvaliacaoAtorsSharedCollection = criterioAvaliacaoAtors),
      );

    this.usuarioEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IUsuarioEmpresa[]>) => res.body ?? []))
      .pipe(
        map((usuarioEmpresas: IUsuarioEmpresa[]) =>
          this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
            usuarioEmpresas,
            this.feedBackContadorParaUsuario?.usuarioEmpresa,
          ),
        ),
      )
      .subscribe((usuarioEmpresas: IUsuarioEmpresa[]) => (this.usuarioEmpresasSharedCollection = usuarioEmpresas));

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.feedBackContadorParaUsuario?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));

    this.ordemServicoService
      .query()
      .pipe(map((res: HttpResponse<IOrdemServico[]>) => res.body ?? []))
      .pipe(
        map((ordemServicos: IOrdemServico[]) =>
          this.ordemServicoService.addOrdemServicoToCollectionIfMissing<IOrdemServico>(
            ordemServicos,
            this.feedBackContadorParaUsuario?.ordemServico,
          ),
        ),
      )
      .subscribe((ordemServicos: IOrdemServico[]) => (this.ordemServicosSharedCollection = ordemServicos));
  }
}
