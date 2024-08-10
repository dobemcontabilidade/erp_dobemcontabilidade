import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { DepartamentoFuncionarioService } from '../service/departamento-funcionario.service';
import { IDepartamentoFuncionario } from '../departamento-funcionario.model';
import { DepartamentoFuncionarioFormService, DepartamentoFuncionarioFormGroup } from './departamento-funcionario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-departamento-funcionario-update',
  templateUrl: './departamento-funcionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepartamentoFuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  departamentoFuncionario: IDepartamentoFuncionario | null = null;

  funcionariosSharedCollection: IFuncionario[] = [];
  departamentosSharedCollection: IDepartamento[] = [];

  protected departamentoFuncionarioService = inject(DepartamentoFuncionarioService);
  protected departamentoFuncionarioFormService = inject(DepartamentoFuncionarioFormService);
  protected funcionarioService = inject(FuncionarioService);
  protected departamentoService = inject(DepartamentoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DepartamentoFuncionarioFormGroup = this.departamentoFuncionarioFormService.createDepartamentoFuncionarioFormGroup();

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  compareDepartamento = (o1: IDepartamento | null, o2: IDepartamento | null): boolean =>
    this.departamentoService.compareDepartamento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departamentoFuncionario }) => {
      this.departamentoFuncionario = departamentoFuncionario;
      if (departamentoFuncionario) {
        this.updateForm(departamentoFuncionario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departamentoFuncionario = this.departamentoFuncionarioFormService.getDepartamentoFuncionario(this.editForm);
    if (departamentoFuncionario.id !== null) {
      this.subscribeToSaveResponse(this.departamentoFuncionarioService.update(departamentoFuncionario));
    } else {
      this.subscribeToSaveResponse(this.departamentoFuncionarioService.create(departamentoFuncionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamentoFuncionario>>): void {
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

  protected updateForm(departamentoFuncionario: IDepartamentoFuncionario): void {
    this.departamentoFuncionario = departamentoFuncionario;
    this.departamentoFuncionarioFormService.resetForm(this.editForm, departamentoFuncionario);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      departamentoFuncionario.funcionario,
    );
    this.departamentosSharedCollection = this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
      this.departamentosSharedCollection,
      departamentoFuncionario.departamento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
            funcionarios,
            this.departamentoFuncionario?.funcionario,
          ),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));

    this.departamentoService
      .query()
      .pipe(map((res: HttpResponse<IDepartamento[]>) => res.body ?? []))
      .pipe(
        map((departamentos: IDepartamento[]) =>
          this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
            departamentos,
            this.departamentoFuncionario?.departamento,
          ),
        ),
      )
      .subscribe((departamentos: IDepartamento[]) => (this.departamentosSharedCollection = departamentos));
  }
}
