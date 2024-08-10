import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEsfera } from 'app/entities/esfera/esfera.model';
import { EsferaService } from 'app/entities/esfera/service/esfera.service';
import { IFrequencia } from 'app/entities/frequencia/frequencia.model';
import { FrequenciaService } from 'app/entities/frequencia/service/frequencia.service';
import { ICompetencia } from 'app/entities/competencia/competencia.model';
import { CompetenciaService } from 'app/entities/competencia/service/competencia.service';
import { TipoTarefaEnum } from 'app/entities/enumerations/tipo-tarefa-enum.model';
import { TarefaService } from '../service/tarefa.service';
import { ITarefa } from '../tarefa.model';
import { TarefaFormService, TarefaFormGroup } from './tarefa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-update',
  templateUrl: './tarefa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaUpdateComponent implements OnInit {
  isSaving = false;
  tarefa: ITarefa | null = null;
  tipoTarefaEnumValues = Object.keys(TipoTarefaEnum);

  esferasSharedCollection: IEsfera[] = [];
  frequenciasSharedCollection: IFrequencia[] = [];
  competenciasSharedCollection: ICompetencia[] = [];

  protected tarefaService = inject(TarefaService);
  protected tarefaFormService = inject(TarefaFormService);
  protected esferaService = inject(EsferaService);
  protected frequenciaService = inject(FrequenciaService);
  protected competenciaService = inject(CompetenciaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaFormGroup = this.tarefaFormService.createTarefaFormGroup();

  compareEsfera = (o1: IEsfera | null, o2: IEsfera | null): boolean => this.esferaService.compareEsfera(o1, o2);

  compareFrequencia = (o1: IFrequencia | null, o2: IFrequencia | null): boolean => this.frequenciaService.compareFrequencia(o1, o2);

  compareCompetencia = (o1: ICompetencia | null, o2: ICompetencia | null): boolean => this.competenciaService.compareCompetencia(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefa }) => {
      this.tarefa = tarefa;
      if (tarefa) {
        this.updateForm(tarefa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefa = this.tarefaFormService.getTarefa(this.editForm);
    if (tarefa.id !== null) {
      this.subscribeToSaveResponse(this.tarefaService.update(tarefa));
    } else {
      this.subscribeToSaveResponse(this.tarefaService.create(tarefa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefa>>): void {
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

  protected updateForm(tarefa: ITarefa): void {
    this.tarefa = tarefa;
    this.tarefaFormService.resetForm(this.editForm, tarefa);

    this.esferasSharedCollection = this.esferaService.addEsferaToCollectionIfMissing<IEsfera>(this.esferasSharedCollection, tarefa.esfera);
    this.frequenciasSharedCollection = this.frequenciaService.addFrequenciaToCollectionIfMissing<IFrequencia>(
      this.frequenciasSharedCollection,
      tarefa.frequencia,
    );
    this.competenciasSharedCollection = this.competenciaService.addCompetenciaToCollectionIfMissing<ICompetencia>(
      this.competenciasSharedCollection,
      tarefa.competencia,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.esferaService
      .query()
      .pipe(map((res: HttpResponse<IEsfera[]>) => res.body ?? []))
      .pipe(map((esferas: IEsfera[]) => this.esferaService.addEsferaToCollectionIfMissing<IEsfera>(esferas, this.tarefa?.esfera)))
      .subscribe((esferas: IEsfera[]) => (this.esferasSharedCollection = esferas));

    this.frequenciaService
      .query()
      .pipe(map((res: HttpResponse<IFrequencia[]>) => res.body ?? []))
      .pipe(
        map((frequencias: IFrequencia[]) =>
          this.frequenciaService.addFrequenciaToCollectionIfMissing<IFrequencia>(frequencias, this.tarefa?.frequencia),
        ),
      )
      .subscribe((frequencias: IFrequencia[]) => (this.frequenciasSharedCollection = frequencias));

    this.competenciaService
      .query()
      .pipe(map((res: HttpResponse<ICompetencia[]>) => res.body ?? []))
      .pipe(
        map((competencias: ICompetencia[]) =>
          this.competenciaService.addCompetenciaToCollectionIfMissing<ICompetencia>(competencias, this.tarefa?.competencia),
        ),
      )
      .subscribe((competencias: ICompetencia[]) => (this.competenciasSharedCollection = competencias));
  }
}
