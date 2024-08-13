import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPerfilAcesso } from '../perfil-acesso.model';
import { PerfilAcessoService } from '../service/perfil-acesso.service';
import { PerfilAcessoFormService, PerfilAcessoFormGroup } from './perfil-acesso-form.service';

@Component({
  standalone: true,
  selector: 'jhi-perfil-acesso-update',
  templateUrl: './perfil-acesso-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PerfilAcessoUpdateComponent implements OnInit {
  isSaving = false;
  perfilAcesso: IPerfilAcesso | null = null;

  protected perfilAcessoService = inject(PerfilAcessoService);
  protected perfilAcessoFormService = inject(PerfilAcessoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PerfilAcessoFormGroup = this.perfilAcessoFormService.createPerfilAcessoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilAcesso }) => {
      this.perfilAcesso = perfilAcesso;
      if (perfilAcesso) {
        this.updateForm(perfilAcesso);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilAcesso = this.perfilAcessoFormService.getPerfilAcesso(this.editForm);
    if (perfilAcesso.id !== null) {
      this.subscribeToSaveResponse(this.perfilAcessoService.update(perfilAcesso));
    } else {
      this.subscribeToSaveResponse(this.perfilAcessoService.create(perfilAcesso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilAcesso>>): void {
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

  protected updateForm(perfilAcesso: IPerfilAcesso): void {
    this.perfilAcesso = perfilAcesso;
    this.perfilAcessoFormService.resetForm(this.editForm, perfilAcesso);
  }
}
