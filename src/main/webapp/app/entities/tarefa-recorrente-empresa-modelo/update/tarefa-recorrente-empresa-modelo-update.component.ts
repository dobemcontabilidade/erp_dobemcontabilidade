import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicoContabilEmpresaModelo } from 'app/entities/servico-contabil-empresa-modelo/servico-contabil-empresa-modelo.model';
import { ServicoContabilEmpresaModeloService } from 'app/entities/servico-contabil-empresa-modelo/service/servico-contabil-empresa-modelo.service';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';
import { TipoRecorrenciaEnum } from 'app/entities/enumerations/tipo-recorrencia-enum.model';
import { TarefaRecorrenteEmpresaModeloService } from '../service/tarefa-recorrente-empresa-modelo.service';
import { ITarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';
import {
  TarefaRecorrenteEmpresaModeloFormService,
  TarefaRecorrenteEmpresaModeloFormGroup,
} from './tarefa-recorrente-empresa-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-recorrente-empresa-modelo-update',
  templateUrl: './tarefa-recorrente-empresa-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaRecorrenteEmpresaModeloUpdateComponent implements OnInit {
  isSaving = false;
  tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo | null = null;
  mesCompetenciaEnumValues = Object.keys(MesCompetenciaEnum);
  tipoRecorrenciaEnumValues = Object.keys(TipoRecorrenciaEnum);

  servicoContabilEmpresaModelosSharedCollection: IServicoContabilEmpresaModelo[] = [];

  protected tarefaRecorrenteEmpresaModeloService = inject(TarefaRecorrenteEmpresaModeloService);
  protected tarefaRecorrenteEmpresaModeloFormService = inject(TarefaRecorrenteEmpresaModeloFormService);
  protected servicoContabilEmpresaModeloService = inject(ServicoContabilEmpresaModeloService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaRecorrenteEmpresaModeloFormGroup =
    this.tarefaRecorrenteEmpresaModeloFormService.createTarefaRecorrenteEmpresaModeloFormGroup();

  compareServicoContabilEmpresaModelo = (o1: IServicoContabilEmpresaModelo | null, o2: IServicoContabilEmpresaModelo | null): boolean =>
    this.servicoContabilEmpresaModeloService.compareServicoContabilEmpresaModelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefaRecorrenteEmpresaModelo }) => {
      this.tarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModelo;
      if (tarefaRecorrenteEmpresaModelo) {
        this.updateForm(tarefaRecorrenteEmpresaModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefaRecorrenteEmpresaModelo = this.tarefaRecorrenteEmpresaModeloFormService.getTarefaRecorrenteEmpresaModelo(this.editForm);
    if (tarefaRecorrenteEmpresaModelo.id !== null) {
      this.subscribeToSaveResponse(this.tarefaRecorrenteEmpresaModeloService.update(tarefaRecorrenteEmpresaModelo));
    } else {
      this.subscribeToSaveResponse(this.tarefaRecorrenteEmpresaModeloService.create(tarefaRecorrenteEmpresaModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefaRecorrenteEmpresaModelo>>): void {
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

  protected updateForm(tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo): void {
    this.tarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModelo;
    this.tarefaRecorrenteEmpresaModeloFormService.resetForm(this.editForm, tarefaRecorrenteEmpresaModelo);

    this.servicoContabilEmpresaModelosSharedCollection =
      this.servicoContabilEmpresaModeloService.addServicoContabilEmpresaModeloToCollectionIfMissing<IServicoContabilEmpresaModelo>(
        this.servicoContabilEmpresaModelosSharedCollection,
        tarefaRecorrenteEmpresaModelo.servicoContabilEmpresaModelo,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.servicoContabilEmpresaModeloService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabilEmpresaModelo[]>) => res.body ?? []))
      .pipe(
        map((servicoContabilEmpresaModelos: IServicoContabilEmpresaModelo[]) =>
          this.servicoContabilEmpresaModeloService.addServicoContabilEmpresaModeloToCollectionIfMissing<IServicoContabilEmpresaModelo>(
            servicoContabilEmpresaModelos,
            this.tarefaRecorrenteEmpresaModelo?.servicoContabilEmpresaModelo,
          ),
        ),
      )
      .subscribe(
        (servicoContabilEmpresaModelos: IServicoContabilEmpresaModelo[]) =>
          (this.servicoContabilEmpresaModelosSharedCollection = servicoContabilEmpresaModelos),
      );
  }
}
