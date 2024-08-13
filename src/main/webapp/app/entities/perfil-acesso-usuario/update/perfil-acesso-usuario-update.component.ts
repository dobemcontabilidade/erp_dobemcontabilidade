import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';
import { PerfilAcessoUsuarioService } from '../service/perfil-acesso-usuario.service';
import { PerfilAcessoUsuarioFormService, PerfilAcessoUsuarioFormGroup } from './perfil-acesso-usuario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-perfil-acesso-usuario-update',
  templateUrl: './perfil-acesso-usuario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PerfilAcessoUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  perfilAcessoUsuario: IPerfilAcessoUsuario | null = null;

  protected perfilAcessoUsuarioService = inject(PerfilAcessoUsuarioService);
  protected perfilAcessoUsuarioFormService = inject(PerfilAcessoUsuarioFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PerfilAcessoUsuarioFormGroup = this.perfilAcessoUsuarioFormService.createPerfilAcessoUsuarioFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilAcessoUsuario }) => {
      this.perfilAcessoUsuario = perfilAcessoUsuario;
      if (perfilAcessoUsuario) {
        this.updateForm(perfilAcessoUsuario);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilAcessoUsuario = this.perfilAcessoUsuarioFormService.getPerfilAcessoUsuario(this.editForm);
    if (perfilAcessoUsuario.id !== null) {
      this.subscribeToSaveResponse(this.perfilAcessoUsuarioService.update(perfilAcessoUsuario));
    } else {
      this.subscribeToSaveResponse(this.perfilAcessoUsuarioService.create(perfilAcessoUsuario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilAcessoUsuario>>): void {
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

  protected updateForm(perfilAcessoUsuario: IPerfilAcessoUsuario): void {
    this.perfilAcessoUsuario = perfilAcessoUsuario;
    this.perfilAcessoUsuarioFormService.resetForm(this.editForm, perfilAcessoUsuario);
  }
}
