import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { ITermoDeAdesao } from 'app/entities/termo-de-adesao/termo-de-adesao.model';
import { TermoDeAdesaoService } from 'app/entities/termo-de-adesao/service/termo-de-adesao.service';
import { TermoAdesaoContadorService } from '../service/termo-adesao-contador.service';
import { ITermoAdesaoContador } from '../termo-adesao-contador.model';
import { TermoAdesaoContadorFormService, TermoAdesaoContadorFormGroup } from './termo-adesao-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-termo-adesao-contador-update',
  templateUrl: './termo-adesao-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TermoAdesaoContadorUpdateComponent implements OnInit {
  isSaving = false;
  termoAdesaoContador: ITermoAdesaoContador | null = null;

  contadorsSharedCollection: IContador[] = [];
  termoDeAdesaosSharedCollection: ITermoDeAdesao[] = [];

  protected termoAdesaoContadorService = inject(TermoAdesaoContadorService);
  protected termoAdesaoContadorFormService = inject(TermoAdesaoContadorFormService);
  protected contadorService = inject(ContadorService);
  protected termoDeAdesaoService = inject(TermoDeAdesaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TermoAdesaoContadorFormGroup = this.termoAdesaoContadorFormService.createTermoAdesaoContadorFormGroup();

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  compareTermoDeAdesao = (o1: ITermoDeAdesao | null, o2: ITermoDeAdesao | null): boolean =>
    this.termoDeAdesaoService.compareTermoDeAdesao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ termoAdesaoContador }) => {
      this.termoAdesaoContador = termoAdesaoContador;
      if (termoAdesaoContador) {
        this.updateForm(termoAdesaoContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const termoAdesaoContador = this.termoAdesaoContadorFormService.getTermoAdesaoContador(this.editForm);
    if (termoAdesaoContador.id !== null) {
      this.subscribeToSaveResponse(this.termoAdesaoContadorService.update(termoAdesaoContador));
    } else {
      this.subscribeToSaveResponse(this.termoAdesaoContadorService.create(termoAdesaoContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITermoAdesaoContador>>): void {
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

  protected updateForm(termoAdesaoContador: ITermoAdesaoContador): void {
    this.termoAdesaoContador = termoAdesaoContador;
    this.termoAdesaoContadorFormService.resetForm(this.editForm, termoAdesaoContador);

    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      termoAdesaoContador.contador,
    );
    this.termoDeAdesaosSharedCollection = this.termoDeAdesaoService.addTermoDeAdesaoToCollectionIfMissing<ITermoDeAdesao>(
      this.termoDeAdesaosSharedCollection,
      termoAdesaoContador.termoDeAdesao,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.termoAdesaoContador?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));

    this.termoDeAdesaoService
      .query()
      .pipe(map((res: HttpResponse<ITermoDeAdesao[]>) => res.body ?? []))
      .pipe(
        map((termoDeAdesaos: ITermoDeAdesao[]) =>
          this.termoDeAdesaoService.addTermoDeAdesaoToCollectionIfMissing<ITermoDeAdesao>(
            termoDeAdesaos,
            this.termoAdesaoContador?.termoDeAdesao,
          ),
        ),
      )
      .subscribe((termoDeAdesaos: ITermoDeAdesao[]) => (this.termoDeAdesaosSharedCollection = termoDeAdesaos));
  }
}
