import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISistema } from 'app/entities/sistema/sistema.model';
import { SistemaService } from 'app/entities/sistema/service/sistema.service';
import { IModulo } from '../modulo.model';
import { ModuloService } from '../service/modulo.service';
import { ModuloFormService, ModuloFormGroup } from './modulo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-modulo-update',
  templateUrl: './modulo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ModuloUpdateComponent implements OnInit {
  isSaving = false;
  modulo: IModulo | null = null;

  sistemasSharedCollection: ISistema[] = [];

  protected moduloService = inject(ModuloService);
  protected moduloFormService = inject(ModuloFormService);
  protected sistemaService = inject(SistemaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ModuloFormGroup = this.moduloFormService.createModuloFormGroup();

  compareSistema = (o1: ISistema | null, o2: ISistema | null): boolean => this.sistemaService.compareSistema(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modulo }) => {
      this.modulo = modulo;
      if (modulo) {
        this.updateForm(modulo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modulo = this.moduloFormService.getModulo(this.editForm);
    if (modulo.id !== null) {
      this.subscribeToSaveResponse(this.moduloService.update(modulo));
    } else {
      this.subscribeToSaveResponse(this.moduloService.create(modulo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModulo>>): void {
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

  protected updateForm(modulo: IModulo): void {
    this.modulo = modulo;
    this.moduloFormService.resetForm(this.editForm, modulo);

    this.sistemasSharedCollection = this.sistemaService.addSistemaToCollectionIfMissing<ISistema>(
      this.sistemasSharedCollection,
      modulo.sistema,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sistemaService
      .query()
      .pipe(map((res: HttpResponse<ISistema[]>) => res.body ?? []))
      .pipe(map((sistemas: ISistema[]) => this.sistemaService.addSistemaToCollectionIfMissing<ISistema>(sistemas, this.modulo?.sistema)))
      .subscribe((sistemas: ISistema[]) => (this.sistemasSharedCollection = sistemas));
  }
}
