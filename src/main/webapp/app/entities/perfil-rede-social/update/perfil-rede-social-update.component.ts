import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TipoRedeSocialEnum } from 'app/entities/enumerations/tipo-rede-social-enum.model';
import { IPerfilRedeSocial } from '../perfil-rede-social.model';
import { PerfilRedeSocialService } from '../service/perfil-rede-social.service';
import { PerfilRedeSocialFormService, PerfilRedeSocialFormGroup } from './perfil-rede-social-form.service';

@Component({
  standalone: true,
  selector: 'jhi-perfil-rede-social-update',
  templateUrl: './perfil-rede-social-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PerfilRedeSocialUpdateComponent implements OnInit {
  isSaving = false;
  perfilRedeSocial: IPerfilRedeSocial | null = null;
  tipoRedeSocialEnumValues = Object.keys(TipoRedeSocialEnum);

  protected perfilRedeSocialService = inject(PerfilRedeSocialService);
  protected perfilRedeSocialFormService = inject(PerfilRedeSocialFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PerfilRedeSocialFormGroup = this.perfilRedeSocialFormService.createPerfilRedeSocialFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilRedeSocial }) => {
      this.perfilRedeSocial = perfilRedeSocial;
      if (perfilRedeSocial) {
        this.updateForm(perfilRedeSocial);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilRedeSocial = this.perfilRedeSocialFormService.getPerfilRedeSocial(this.editForm);
    if (perfilRedeSocial.id !== null) {
      this.subscribeToSaveResponse(this.perfilRedeSocialService.update(perfilRedeSocial));
    } else {
      this.subscribeToSaveResponse(this.perfilRedeSocialService.create(perfilRedeSocial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilRedeSocial>>): void {
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

  protected updateForm(perfilRedeSocial: IPerfilRedeSocial): void {
    this.perfilRedeSocial = perfilRedeSocial;
    this.perfilRedeSocialFormService.resetForm(this.editForm, perfilRedeSocial);
  }
}
