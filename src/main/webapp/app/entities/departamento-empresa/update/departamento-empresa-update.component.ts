import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { DepartamentoEmpresaService } from '../service/departamento-empresa.service';
import { IDepartamentoEmpresa } from '../departamento-empresa.model';
import { DepartamentoEmpresaFormService, DepartamentoEmpresaFormGroup } from './departamento-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-departamento-empresa-update',
  templateUrl: './departamento-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepartamentoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  departamentoEmpresa: IDepartamentoEmpresa | null = null;

  departamentosSharedCollection: IDepartamento[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  contadorsSharedCollection: IContador[] = [];

  protected departamentoEmpresaService = inject(DepartamentoEmpresaService);
  protected departamentoEmpresaFormService = inject(DepartamentoEmpresaFormService);
  protected departamentoService = inject(DepartamentoService);
  protected empresaService = inject(EmpresaService);
  protected contadorService = inject(ContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DepartamentoEmpresaFormGroup = this.departamentoEmpresaFormService.createDepartamentoEmpresaFormGroup();

  compareDepartamento = (o1: IDepartamento | null, o2: IDepartamento | null): boolean =>
    this.departamentoService.compareDepartamento(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departamentoEmpresa }) => {
      this.departamentoEmpresa = departamentoEmpresa;
      if (departamentoEmpresa) {
        this.updateForm(departamentoEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departamentoEmpresa = this.departamentoEmpresaFormService.getDepartamentoEmpresa(this.editForm);
    if (departamentoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.departamentoEmpresaService.update(departamentoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.departamentoEmpresaService.create(departamentoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamentoEmpresa>>): void {
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

  protected updateForm(departamentoEmpresa: IDepartamentoEmpresa): void {
    this.departamentoEmpresa = departamentoEmpresa;
    this.departamentoEmpresaFormService.resetForm(this.editForm, departamentoEmpresa);

    this.departamentosSharedCollection = this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
      this.departamentosSharedCollection,
      departamentoEmpresa.departamento,
    );
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      departamentoEmpresa.empresa,
    );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      departamentoEmpresa.contador,
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
            this.departamentoEmpresa?.departamento,
          ),
        ),
      )
      .subscribe((departamentos: IDepartamento[]) => (this.departamentosSharedCollection = departamentos));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.departamentoEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.departamentoEmpresa?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));
  }
}
