import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPerfilContador } from '../perfil-contador.model';
import { PerfilContadorService } from '../service/perfil-contador.service';
import { PerfilContadorFormService, PerfilContadorFormGroup } from './perfil-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-perfil-contador-update',
  templateUrl: './perfil-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PerfilContadorUpdateComponent implements OnInit {
  isSaving = false;
  perfilContador: IPerfilContador | null = null;

  protected perfilContadorService = inject(PerfilContadorService);
  protected perfilContadorFormService = inject(PerfilContadorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PerfilContadorFormGroup = this.perfilContadorFormService.createPerfilContadorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilContador }) => {
      this.perfilContador = perfilContador;
      if (perfilContador) {
        this.updateForm(perfilContador);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilContador = this.perfilContadorFormService.getPerfilContador(this.editForm);
    if (perfilContador.id !== null) {
      this.subscribeToSaveResponse(this.perfilContadorService.update(perfilContador));
    } else {
      this.subscribeToSaveResponse(this.perfilContadorService.create(perfilContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilContador>>): void {
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

  protected updateForm(perfilContador: IPerfilContador): void {
    this.perfilContador = perfilContador;
    this.perfilContadorFormService.resetForm(this.editForm, perfilContador);
  }
}
