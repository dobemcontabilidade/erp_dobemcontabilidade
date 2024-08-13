import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICriterioAvaliacao } from 'app/entities/criterio-avaliacao/criterio-avaliacao.model';
import { CriterioAvaliacaoService } from 'app/entities/criterio-avaliacao/service/criterio-avaliacao.service';
import { IAtorAvaliado } from 'app/entities/ator-avaliado/ator-avaliado.model';
import { AtorAvaliadoService } from 'app/entities/ator-avaliado/service/ator-avaliado.service';
import { CriterioAvaliacaoAtorService } from '../service/criterio-avaliacao-ator.service';
import { ICriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorFormService, CriterioAvaliacaoAtorFormGroup } from './criterio-avaliacao-ator-form.service';

@Component({
  standalone: true,
  selector: 'jhi-criterio-avaliacao-ator-update',
  templateUrl: './criterio-avaliacao-ator-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CriterioAvaliacaoAtorUpdateComponent implements OnInit {
  isSaving = false;
  criterioAvaliacaoAtor: ICriterioAvaliacaoAtor | null = null;

  criterioAvaliacaosSharedCollection: ICriterioAvaliacao[] = [];
  atorAvaliadosSharedCollection: IAtorAvaliado[] = [];

  protected criterioAvaliacaoAtorService = inject(CriterioAvaliacaoAtorService);
  protected criterioAvaliacaoAtorFormService = inject(CriterioAvaliacaoAtorFormService);
  protected criterioAvaliacaoService = inject(CriterioAvaliacaoService);
  protected atorAvaliadoService = inject(AtorAvaliadoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CriterioAvaliacaoAtorFormGroup = this.criterioAvaliacaoAtorFormService.createCriterioAvaliacaoAtorFormGroup();

  compareCriterioAvaliacao = (o1: ICriterioAvaliacao | null, o2: ICriterioAvaliacao | null): boolean =>
    this.criterioAvaliacaoService.compareCriterioAvaliacao(o1, o2);

  compareAtorAvaliado = (o1: IAtorAvaliado | null, o2: IAtorAvaliado | null): boolean =>
    this.atorAvaliadoService.compareAtorAvaliado(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criterioAvaliacaoAtor }) => {
      this.criterioAvaliacaoAtor = criterioAvaliacaoAtor;
      if (criterioAvaliacaoAtor) {
        this.updateForm(criterioAvaliacaoAtor);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const criterioAvaliacaoAtor = this.criterioAvaliacaoAtorFormService.getCriterioAvaliacaoAtor(this.editForm);
    if (criterioAvaliacaoAtor.id !== null) {
      this.subscribeToSaveResponse(this.criterioAvaliacaoAtorService.update(criterioAvaliacaoAtor));
    } else {
      this.subscribeToSaveResponse(this.criterioAvaliacaoAtorService.create(criterioAvaliacaoAtor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriterioAvaliacaoAtor>>): void {
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

  protected updateForm(criterioAvaliacaoAtor: ICriterioAvaliacaoAtor): void {
    this.criterioAvaliacaoAtor = criterioAvaliacaoAtor;
    this.criterioAvaliacaoAtorFormService.resetForm(this.editForm, criterioAvaliacaoAtor);

    this.criterioAvaliacaosSharedCollection = this.criterioAvaliacaoService.addCriterioAvaliacaoToCollectionIfMissing<ICriterioAvaliacao>(
      this.criterioAvaliacaosSharedCollection,
      criterioAvaliacaoAtor.criterioAvaliacao,
    );
    this.atorAvaliadosSharedCollection = this.atorAvaliadoService.addAtorAvaliadoToCollectionIfMissing<IAtorAvaliado>(
      this.atorAvaliadosSharedCollection,
      criterioAvaliacaoAtor.atorAvaliado,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.criterioAvaliacaoService
      .query()
      .pipe(map((res: HttpResponse<ICriterioAvaliacao[]>) => res.body ?? []))
      .pipe(
        map((criterioAvaliacaos: ICriterioAvaliacao[]) =>
          this.criterioAvaliacaoService.addCriterioAvaliacaoToCollectionIfMissing<ICriterioAvaliacao>(
            criterioAvaliacaos,
            this.criterioAvaliacaoAtor?.criterioAvaliacao,
          ),
        ),
      )
      .subscribe((criterioAvaliacaos: ICriterioAvaliacao[]) => (this.criterioAvaliacaosSharedCollection = criterioAvaliacaos));

    this.atorAvaliadoService
      .query()
      .pipe(map((res: HttpResponse<IAtorAvaliado[]>) => res.body ?? []))
      .pipe(
        map((atorAvaliados: IAtorAvaliado[]) =>
          this.atorAvaliadoService.addAtorAvaliadoToCollectionIfMissing<IAtorAvaliado>(
            atorAvaliados,
            this.criterioAvaliacaoAtor?.atorAvaliado,
          ),
        ),
      )
      .subscribe((atorAvaliados: IAtorAvaliado[]) => (this.atorAvaliadosSharedCollection = atorAvaliados));
  }
}
