import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { DepartamentoContadorService } from '../service/departamento-contador.service';
import { IDepartamentoContador } from '../departamento-contador.model';
import { DepartamentoContadorFormService, DepartamentoContadorFormGroup } from './departamento-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-departamento-contador-update',
  templateUrl: './departamento-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepartamentoContadorUpdateComponent implements OnInit {
  isSaving = false;
  departamentoContador: IDepartamentoContador | null = null;

  departamentosSharedCollection: IDepartamento[] = [];
  contadorsSharedCollection: IContador[] = [];

  protected departamentoContadorService = inject(DepartamentoContadorService);
  protected departamentoContadorFormService = inject(DepartamentoContadorFormService);
  protected departamentoService = inject(DepartamentoService);
  protected contadorService = inject(ContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DepartamentoContadorFormGroup = this.departamentoContadorFormService.createDepartamentoContadorFormGroup();

  compareDepartamento = (o1: IDepartamento | null, o2: IDepartamento | null): boolean =>
    this.departamentoService.compareDepartamento(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departamentoContador }) => {
      this.departamentoContador = departamentoContador;
      if (departamentoContador) {
        this.updateForm(departamentoContador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departamentoContador = this.departamentoContadorFormService.getDepartamentoContador(this.editForm);
    if (departamentoContador.id !== null) {
      this.subscribeToSaveResponse(this.departamentoContadorService.update(departamentoContador));
    } else {
      this.subscribeToSaveResponse(this.departamentoContadorService.create(departamentoContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamentoContador>>): void {
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

  protected updateForm(departamentoContador: IDepartamentoContador): void {
    this.departamentoContador = departamentoContador;
    this.departamentoContadorFormService.resetForm(this.editForm, departamentoContador);

    this.departamentosSharedCollection = this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
      this.departamentosSharedCollection,
      departamentoContador.departamento,
    );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      departamentoContador.contador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.departamentoService
      .query()
      .pipe(map((res: HttpResponse<IDepartamento[]>) => res.body ?? []))
      .pipe(
        map((departamentos: IDepartamento[]) =>
          this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
            departamentos,
            this.departamentoContador?.departamento,
          ),
        ),
      )
      .subscribe((departamentos: IDepartamento[]) => (this.departamentosSharedCollection = departamentos));

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.departamentoContador?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));
  }
}
