import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';
import { PerfilContadorService } from 'app/entities/perfil-contador/service/perfil-contador.service';
import { PerfilContadorDepartamentoService } from '../service/perfil-contador-departamento.service';
import { IPerfilContadorDepartamento } from '../perfil-contador-departamento.model';
import { PerfilContadorDepartamentoFormService, PerfilContadorDepartamentoFormGroup } from './perfil-contador-departamento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-perfil-contador-departamento-update',
  templateUrl: './perfil-contador-departamento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PerfilContadorDepartamentoUpdateComponent implements OnInit {
  isSaving = false;
  perfilContadorDepartamento: IPerfilContadorDepartamento | null = null;

  departamentosSharedCollection: IDepartamento[] = [];
  perfilContadorsSharedCollection: IPerfilContador[] = [];

  protected perfilContadorDepartamentoService = inject(PerfilContadorDepartamentoService);
  protected perfilContadorDepartamentoFormService = inject(PerfilContadorDepartamentoFormService);
  protected departamentoService = inject(DepartamentoService);
  protected perfilContadorService = inject(PerfilContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PerfilContadorDepartamentoFormGroup = this.perfilContadorDepartamentoFormService.createPerfilContadorDepartamentoFormGroup();

  compareDepartamento = (o1: IDepartamento | null, o2: IDepartamento | null): boolean =>
    this.departamentoService.compareDepartamento(o1, o2);

  comparePerfilContador = (o1: IPerfilContador | null, o2: IPerfilContador | null): boolean =>
    this.perfilContadorService.comparePerfilContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilContadorDepartamento }) => {
      this.perfilContadorDepartamento = perfilContadorDepartamento;
      if (perfilContadorDepartamento) {
        this.updateForm(perfilContadorDepartamento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilContadorDepartamento = this.perfilContadorDepartamentoFormService.getPerfilContadorDepartamento(this.editForm);
    if (perfilContadorDepartamento.id !== null) {
      this.subscribeToSaveResponse(this.perfilContadorDepartamentoService.update(perfilContadorDepartamento));
    } else {
      this.subscribeToSaveResponse(this.perfilContadorDepartamentoService.create(perfilContadorDepartamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilContadorDepartamento>>): void {
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

  protected updateForm(perfilContadorDepartamento: IPerfilContadorDepartamento): void {
    this.perfilContadorDepartamento = perfilContadorDepartamento;
    this.perfilContadorDepartamentoFormService.resetForm(this.editForm, perfilContadorDepartamento);

    this.departamentosSharedCollection = this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
      this.departamentosSharedCollection,
      perfilContadorDepartamento.departamento,
    );
    this.perfilContadorsSharedCollection = this.perfilContadorService.addPerfilContadorToCollectionIfMissing<IPerfilContador>(
      this.perfilContadorsSharedCollection,
      perfilContadorDepartamento.perfilContador,
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
            this.perfilContadorDepartamento?.departamento,
          ),
        ),
      )
      .subscribe((departamentos: IDepartamento[]) => (this.departamentosSharedCollection = departamentos));

    this.perfilContadorService
      .query()
      .pipe(map((res: HttpResponse<IPerfilContador[]>) => res.body ?? []))
      .pipe(
        map((perfilContadors: IPerfilContador[]) =>
          this.perfilContadorService.addPerfilContadorToCollectionIfMissing<IPerfilContador>(
            perfilContadors,
            this.perfilContadorDepartamento?.perfilContador,
          ),
        ),
      )
      .subscribe((perfilContadors: IPerfilContador[]) => (this.perfilContadorsSharedCollection = perfilContadors));
  }
}
