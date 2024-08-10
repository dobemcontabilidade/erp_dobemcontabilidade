import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IBanco } from 'app/entities/banco/banco.model';
import { BancoService } from 'app/entities/banco/service/banco.service';
import { BancoContadorService } from '../service/banco-contador.service';
import { IBancoContador } from '../banco-contador.model';
import { BancoContadorFormService, BancoContadorFormGroup } from './banco-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-banco-contador-update',
  templateUrl: './banco-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BancoContadorUpdateComponent implements OnInit {
  isSaving = false;
  bancoContador: IBancoContador | null = null;

  contadorsSharedCollection: IContador[] = [];
  bancosSharedCollection: IBanco[] = [];

  protected bancoContadorService = inject(BancoContadorService);
  protected bancoContadorFormService = inject(BancoContadorFormService);
  protected contadorService = inject(ContadorService);
  protected bancoService = inject(BancoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BancoContadorFormGroup = this.bancoContadorFormService.createBancoContadorFormGroup();

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  compareBanco = (o1: IBanco | null, o2: IBanco | null): boolean => this.bancoService.compareBanco(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bancoContador }) => {
      this.bancoContador = bancoContador;
      if (bancoContador) {
        this.updateForm(bancoContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bancoContador = this.bancoContadorFormService.getBancoContador(this.editForm);
    if (bancoContador.id !== null) {
      this.subscribeToSaveResponse(this.bancoContadorService.update(bancoContador));
    } else {
      this.subscribeToSaveResponse(this.bancoContadorService.create(bancoContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBancoContador>>): void {
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

  protected updateForm(bancoContador: IBancoContador): void {
    this.bancoContador = bancoContador;
    this.bancoContadorFormService.resetForm(this.editForm, bancoContador);

    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      bancoContador.contador,
    );
    this.bancosSharedCollection = this.bancoService.addBancoToCollectionIfMissing<IBanco>(this.bancosSharedCollection, bancoContador.banco);
  }

  protected loadRelationshipsOptions(): void {
    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.bancoContador?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));

    this.bancoService
      .query()
      .pipe(map((res: HttpResponse<IBanco[]>) => res.body ?? []))
      .pipe(map((bancos: IBanco[]) => this.bancoService.addBancoToCollectionIfMissing<IBanco>(bancos, this.bancoContador?.banco)))
      .subscribe((bancos: IBanco[]) => (this.bancosSharedCollection = bancos));
  }
}
