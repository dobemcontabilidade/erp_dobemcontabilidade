import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICriterioAvaliacao } from '../criterio-avaliacao.model';
import { CriterioAvaliacaoService } from '../service/criterio-avaliacao.service';
import { CriterioAvaliacaoFormService, CriterioAvaliacaoFormGroup } from './criterio-avaliacao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-criterio-avaliacao-update',
  templateUrl: './criterio-avaliacao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CriterioAvaliacaoUpdateComponent implements OnInit {
  isSaving = false;
  criterioAvaliacao: ICriterioAvaliacao | null = null;

  protected criterioAvaliacaoService = inject(CriterioAvaliacaoService);
  protected criterioAvaliacaoFormService = inject(CriterioAvaliacaoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CriterioAvaliacaoFormGroup = this.criterioAvaliacaoFormService.createCriterioAvaliacaoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criterioAvaliacao }) => {
      this.criterioAvaliacao = criterioAvaliacao;
      if (criterioAvaliacao) {
        this.updateForm(criterioAvaliacao);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const criterioAvaliacao = this.criterioAvaliacaoFormService.getCriterioAvaliacao(this.editForm);
    if (criterioAvaliacao.id !== null) {
      this.subscribeToSaveResponse(this.criterioAvaliacaoService.update(criterioAvaliacao));
    } else {
      this.subscribeToSaveResponse(this.criterioAvaliacaoService.create(criterioAvaliacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriterioAvaliacao>>): void {
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

  protected updateForm(criterioAvaliacao: ICriterioAvaliacao): void {
    this.criterioAvaliacao = criterioAvaliacao;
    this.criterioAvaliacaoFormService.resetForm(this.editForm, criterioAvaliacao);
  }
}
