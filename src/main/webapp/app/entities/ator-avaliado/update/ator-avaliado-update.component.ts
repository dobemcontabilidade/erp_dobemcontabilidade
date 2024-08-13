import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAtorAvaliado } from '../ator-avaliado.model';
import { AtorAvaliadoService } from '../service/ator-avaliado.service';
import { AtorAvaliadoFormService, AtorAvaliadoFormGroup } from './ator-avaliado-form.service';

@Component({
  standalone: true,
  selector: 'jhi-ator-avaliado-update',
  templateUrl: './ator-avaliado-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AtorAvaliadoUpdateComponent implements OnInit {
  isSaving = false;
  atorAvaliado: IAtorAvaliado | null = null;

  protected atorAvaliadoService = inject(AtorAvaliadoService);
  protected atorAvaliadoFormService = inject(AtorAvaliadoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AtorAvaliadoFormGroup = this.atorAvaliadoFormService.createAtorAvaliadoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ atorAvaliado }) => {
      this.atorAvaliado = atorAvaliado;
      if (atorAvaliado) {
        this.updateForm(atorAvaliado);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const atorAvaliado = this.atorAvaliadoFormService.getAtorAvaliado(this.editForm);
    if (atorAvaliado.id !== null) {
      this.subscribeToSaveResponse(this.atorAvaliadoService.update(atorAvaliado));
    } else {
      this.subscribeToSaveResponse(this.atorAvaliadoService.create(atorAvaliado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtorAvaliado>>): void {
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

  protected updateForm(atorAvaliado: IAtorAvaliado): void {
    this.atorAvaliado = atorAvaliado;
    this.atorAvaliadoFormService.resetForm(this.editForm, atorAvaliado);
  }
}
