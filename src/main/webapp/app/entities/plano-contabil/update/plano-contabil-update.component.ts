import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SituacaoPlanoContabilEnum } from 'app/entities/enumerations/situacao-plano-contabil-enum.model';
import { IPlanoContabil } from '../plano-contabil.model';
import { PlanoContabilService } from '../service/plano-contabil.service';
import { PlanoContabilFormService, PlanoContabilFormGroup } from './plano-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-plano-contabil-update',
  templateUrl: './plano-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlanoContabilUpdateComponent implements OnInit {
  isSaving = false;
  planoContabil: IPlanoContabil | null = null;
  situacaoPlanoContabilEnumValues = Object.keys(SituacaoPlanoContabilEnum);

  protected planoContabilService = inject(PlanoContabilService);
  protected planoContabilFormService = inject(PlanoContabilFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlanoContabilFormGroup = this.planoContabilFormService.createPlanoContabilFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoContabil }) => {
      this.planoContabil = planoContabil;
      if (planoContabil) {
        this.updateForm(planoContabil);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoContabil = this.planoContabilFormService.getPlanoContabil(this.editForm);
    if (planoContabil.id !== null) {
      this.subscribeToSaveResponse(this.planoContabilService.update(planoContabil));
    } else {
      this.subscribeToSaveResponse(this.planoContabilService.create(planoContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoContabil>>): void {
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

  protected updateForm(planoContabil: IPlanoContabil): void {
    this.planoContabil = planoContabil;
    this.planoContabilFormService.resetForm(this.editForm, planoContabil);
  }
}
