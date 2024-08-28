import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SituacaoPlanoContabilEnum } from 'app/entities/enumerations/situacao-plano-contabil-enum.model';
import { IPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from '../service/plano-assinatura-contabil.service';
import { PlanoAssinaturaContabilFormService, PlanoAssinaturaContabilFormGroup } from './plano-assinatura-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-plano-assinatura-contabil-update',
  templateUrl: './plano-assinatura-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlanoAssinaturaContabilUpdateComponent implements OnInit {
  isSaving = false;
  planoAssinaturaContabil: IPlanoAssinaturaContabil | null = null;
  situacaoPlanoContabilEnumValues = Object.keys(SituacaoPlanoContabilEnum);

  protected planoAssinaturaContabilService = inject(PlanoAssinaturaContabilService);
  protected planoAssinaturaContabilFormService = inject(PlanoAssinaturaContabilFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlanoAssinaturaContabilFormGroup = this.planoAssinaturaContabilFormService.createPlanoAssinaturaContabilFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAssinaturaContabil }) => {
      this.planoAssinaturaContabil = planoAssinaturaContabil;
      if (planoAssinaturaContabil) {
        this.updateForm(planoAssinaturaContabil);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoAssinaturaContabil = this.planoAssinaturaContabilFormService.getPlanoAssinaturaContabil(this.editForm);
    if (planoAssinaturaContabil.id !== null) {
      this.subscribeToSaveResponse(this.planoAssinaturaContabilService.update(planoAssinaturaContabil));
    } else {
      this.subscribeToSaveResponse(this.planoAssinaturaContabilService.create(planoAssinaturaContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoAssinaturaContabil>>): void {
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

  protected updateForm(planoAssinaturaContabil: IPlanoAssinaturaContabil): void {
    this.planoAssinaturaContabil = planoAssinaturaContabil;
    this.planoAssinaturaContabilFormService.resetForm(this.editForm, planoAssinaturaContabil);
  }
}
