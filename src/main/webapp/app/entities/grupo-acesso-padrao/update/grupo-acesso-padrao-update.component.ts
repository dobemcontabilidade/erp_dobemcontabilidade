import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from '../service/grupo-acesso-padrao.service';
import { GrupoAcessoPadraoFormService, GrupoAcessoPadraoFormGroup } from './grupo-acesso-padrao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-padrao-update',
  templateUrl: './grupo-acesso-padrao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GrupoAcessoPadraoUpdateComponent implements OnInit {
  isSaving = false;
  grupoAcessoPadrao: IGrupoAcessoPadrao | null = null;

  protected grupoAcessoPadraoService = inject(GrupoAcessoPadraoService);
  protected grupoAcessoPadraoFormService = inject(GrupoAcessoPadraoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GrupoAcessoPadraoFormGroup = this.grupoAcessoPadraoFormService.createGrupoAcessoPadraoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoAcessoPadrao }) => {
      this.grupoAcessoPadrao = grupoAcessoPadrao;
      if (grupoAcessoPadrao) {
        this.updateForm(grupoAcessoPadrao);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoAcessoPadrao = this.grupoAcessoPadraoFormService.getGrupoAcessoPadrao(this.editForm);
    if (grupoAcessoPadrao.id !== null) {
      this.subscribeToSaveResponse(this.grupoAcessoPadraoService.update(grupoAcessoPadrao));
    } else {
      this.subscribeToSaveResponse(this.grupoAcessoPadraoService.create(grupoAcessoPadrao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoAcessoPadrao>>): void {
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

  protected updateForm(grupoAcessoPadrao: IGrupoAcessoPadrao): void {
    this.grupoAcessoPadrao = grupoAcessoPadrao;
    this.grupoAcessoPadraoFormService.resetForm(this.editForm, grupoAcessoPadrao);
  }
}
