import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SituacaoPlanoContaAzul } from 'app/entities/enumerations/situacao-plano-conta-azul.model';
import { IPlanoContaAzul } from '../plano-conta-azul.model';
import { PlanoContaAzulService } from '../service/plano-conta-azul.service';
import { PlanoContaAzulFormService, PlanoContaAzulFormGroup } from './plano-conta-azul-form.service';

@Component({
  standalone: true,
  selector: 'jhi-plano-conta-azul-update',
  templateUrl: './plano-conta-azul-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlanoContaAzulUpdateComponent implements OnInit {
  isSaving = false;
  planoContaAzul: IPlanoContaAzul | null = null;
  situacaoPlanoContaAzulValues = Object.keys(SituacaoPlanoContaAzul);

  protected planoContaAzulService = inject(PlanoContaAzulService);
  protected planoContaAzulFormService = inject(PlanoContaAzulFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlanoContaAzulFormGroup = this.planoContaAzulFormService.createPlanoContaAzulFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoContaAzul }) => {
      this.planoContaAzul = planoContaAzul;
      if (planoContaAzul) {
        this.updateForm(planoContaAzul);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoContaAzul = this.planoContaAzulFormService.getPlanoContaAzul(this.editForm);
    if (planoContaAzul.id !== null) {
      this.subscribeToSaveResponse(this.planoContaAzulService.update(planoContaAzul));
    } else {
      this.subscribeToSaveResponse(this.planoContaAzulService.create(planoContaAzul));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoContaAzul>>): void {
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

  protected updateForm(planoContaAzul: IPlanoContaAzul): void {
    this.planoContaAzul = planoContaAzul;
    this.planoContaAzulFormService.resetForm(this.editForm, planoContaAzul);
  }
}
