import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompetencia } from '../competencia.model';
import { CompetenciaService } from '../service/competencia.service';
import { CompetenciaFormService, CompetenciaFormGroup } from './competencia-form.service';

@Component({
  standalone: true,
  selector: 'jhi-competencia-update',
  templateUrl: './competencia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CompetenciaUpdateComponent implements OnInit {
  isSaving = false;
  competencia: ICompetencia | null = null;

  protected competenciaService = inject(CompetenciaService);
  protected competenciaFormService = inject(CompetenciaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CompetenciaFormGroup = this.competenciaFormService.createCompetenciaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competencia }) => {
      this.competencia = competencia;
      if (competencia) {
        this.updateForm(competencia);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competencia = this.competenciaFormService.getCompetencia(this.editForm);
    if (competencia.id !== null) {
      this.subscribeToSaveResponse(this.competenciaService.update(competencia));
    } else {
      this.subscribeToSaveResponse(this.competenciaService.create(competencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetencia>>): void {
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

  protected updateForm(competencia: ICompetencia): void {
    this.competencia = competencia;
    this.competenciaFormService.resetForm(this.editForm, competencia);
  }
}
