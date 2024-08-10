import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IAvaliacao } from 'app/entities/avaliacao/avaliacao.model';
import { AvaliacaoService } from 'app/entities/avaliacao/service/avaliacao.service';
import { AvaliacaoContadorService } from '../service/avaliacao-contador.service';
import { IAvaliacaoContador } from '../avaliacao-contador.model';
import { AvaliacaoContadorFormService, AvaliacaoContadorFormGroup } from './avaliacao-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-avaliacao-contador-update',
  templateUrl: './avaliacao-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AvaliacaoContadorUpdateComponent implements OnInit {
  isSaving = false;
  avaliacaoContador: IAvaliacaoContador | null = null;

  contadorsSharedCollection: IContador[] = [];
  avaliacaosSharedCollection: IAvaliacao[] = [];

  protected avaliacaoContadorService = inject(AvaliacaoContadorService);
  protected avaliacaoContadorFormService = inject(AvaliacaoContadorFormService);
  protected contadorService = inject(ContadorService);
  protected avaliacaoService = inject(AvaliacaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AvaliacaoContadorFormGroup = this.avaliacaoContadorFormService.createAvaliacaoContadorFormGroup();

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  compareAvaliacao = (o1: IAvaliacao | null, o2: IAvaliacao | null): boolean => this.avaliacaoService.compareAvaliacao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avaliacaoContador }) => {
      this.avaliacaoContador = avaliacaoContador;
      if (avaliacaoContador) {
        this.updateForm(avaliacaoContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avaliacaoContador = this.avaliacaoContadorFormService.getAvaliacaoContador(this.editForm);
    if (avaliacaoContador.id !== null) {
      this.subscribeToSaveResponse(this.avaliacaoContadorService.update(avaliacaoContador));
    } else {
      this.subscribeToSaveResponse(this.avaliacaoContadorService.create(avaliacaoContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvaliacaoContador>>): void {
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

  protected updateForm(avaliacaoContador: IAvaliacaoContador): void {
    this.avaliacaoContador = avaliacaoContador;
    this.avaliacaoContadorFormService.resetForm(this.editForm, avaliacaoContador);

    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      avaliacaoContador.contador,
    );
    this.avaliacaosSharedCollection = this.avaliacaoService.addAvaliacaoToCollectionIfMissing<IAvaliacao>(
      this.avaliacaosSharedCollection,
      avaliacaoContador.avaliacao,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.avaliacaoContador?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));

    this.avaliacaoService
      .query()
      .pipe(map((res: HttpResponse<IAvaliacao[]>) => res.body ?? []))
      .pipe(
        map((avaliacaos: IAvaliacao[]) =>
          this.avaliacaoService.addAvaliacaoToCollectionIfMissing<IAvaliacao>(avaliacaos, this.avaliacaoContador?.avaliacao),
        ),
      )
      .subscribe((avaliacaos: IAvaliacao[]) => (this.avaliacaosSharedCollection = avaliacaos));
  }
}
