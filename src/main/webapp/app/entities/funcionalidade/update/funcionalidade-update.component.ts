import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IModulo } from 'app/entities/modulo/modulo.model';
import { ModuloService } from 'app/entities/modulo/service/modulo.service';
import { IFuncionalidade } from '../funcionalidade.model';
import { FuncionalidadeService } from '../service/funcionalidade.service';
import { FuncionalidadeFormService, FuncionalidadeFormGroup } from './funcionalidade-form.service';

@Component({
  standalone: true,
  selector: 'jhi-funcionalidade-update',
  templateUrl: './funcionalidade-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuncionalidadeUpdateComponent implements OnInit {
  isSaving = false;
  funcionalidade: IFuncionalidade | null = null;

  modulosSharedCollection: IModulo[] = [];

  protected funcionalidadeService = inject(FuncionalidadeService);
  protected funcionalidadeFormService = inject(FuncionalidadeFormService);
  protected moduloService = inject(ModuloService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuncionalidadeFormGroup = this.funcionalidadeFormService.createFuncionalidadeFormGroup();

  compareModulo = (o1: IModulo | null, o2: IModulo | null): boolean => this.moduloService.compareModulo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcionalidade }) => {
      this.funcionalidade = funcionalidade;
      if (funcionalidade) {
        this.updateForm(funcionalidade);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcionalidade = this.funcionalidadeFormService.getFuncionalidade(this.editForm);
    if (funcionalidade.id !== null) {
      this.subscribeToSaveResponse(this.funcionalidadeService.update(funcionalidade));
    } else {
      this.subscribeToSaveResponse(this.funcionalidadeService.create(funcionalidade));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionalidade>>): void {
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

  protected updateForm(funcionalidade: IFuncionalidade): void {
    this.funcionalidade = funcionalidade;
    this.funcionalidadeFormService.resetForm(this.editForm, funcionalidade);

    this.modulosSharedCollection = this.moduloService.addModuloToCollectionIfMissing<IModulo>(
      this.modulosSharedCollection,
      funcionalidade.modulo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.moduloService
      .query()
      .pipe(map((res: HttpResponse<IModulo[]>) => res.body ?? []))
      .pipe(map((modulos: IModulo[]) => this.moduloService.addModuloToCollectionIfMissing<IModulo>(modulos, this.funcionalidade?.modulo)))
      .subscribe((modulos: IModulo[]) => (this.modulosSharedCollection = modulos));
  }
}
