import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';
import { PerfilContadorService } from 'app/entities/perfil-contador/service/perfil-contador.service';
import { ContadorService } from '../service/contador.service';
import { IContador } from '../contador.model';
import { ContadorFormService, ContadorFormGroup } from './contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contador-update',
  templateUrl: './contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContadorUpdateComponent implements OnInit {
  isSaving = false;
  contador: IContador | null = null;

  pessoasCollection: IPessoa[] = [];
  perfilContadorsSharedCollection: IPerfilContador[] = [];

  protected contadorService = inject(ContadorService);
  protected contadorFormService = inject(ContadorFormService);
  protected pessoaService = inject(PessoaService);
  protected perfilContadorService = inject(PerfilContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContadorFormGroup = this.contadorFormService.createContadorFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  comparePerfilContador = (o1: IPerfilContador | null, o2: IPerfilContador | null): boolean =>
    this.perfilContadorService.comparePerfilContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contador }) => {
      this.contador = contador;
      if (contador) {
        this.updateForm(contador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contador = this.contadorFormService.getContador(this.editForm);
    if (contador.id !== null) {
      this.subscribeToSaveResponse(this.contadorService.update(contador));
    } else {
      this.subscribeToSaveResponse(this.contadorService.create(contador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContador>>): void {
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

  protected updateForm(contador: IContador): void {
    this.contador = contador;
    this.contadorFormService.resetForm(this.editForm, contador);

    this.pessoasCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(this.pessoasCollection, contador.pessoa);
    this.perfilContadorsSharedCollection = this.perfilContadorService.addPerfilContadorToCollectionIfMissing<IPerfilContador>(
      this.perfilContadorsSharedCollection,
      contador.perfilContador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query({ 'contadorId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.contador?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasCollection = pessoas));

    this.perfilContadorService
      .query()
      .pipe(map((res: HttpResponse<IPerfilContador[]>) => res.body ?? []))
      .pipe(
        map((perfilContadors: IPerfilContador[]) =>
          this.perfilContadorService.addPerfilContadorToCollectionIfMissing<IPerfilContador>(
            perfilContadors,
            this.contador?.perfilContador,
          ),
        ),
      )
      .subscribe((perfilContadors: IPerfilContador[]) => (this.perfilContadorsSharedCollection = perfilContadors));
  }
}
