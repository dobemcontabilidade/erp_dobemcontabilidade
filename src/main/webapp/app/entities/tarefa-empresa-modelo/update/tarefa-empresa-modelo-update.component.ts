import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { TarefaEmpresaModeloService } from '../service/tarefa-empresa-modelo.service';
import { ITarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';
import { TarefaEmpresaModeloFormService, TarefaEmpresaModeloFormGroup } from './tarefa-empresa-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-empresa-modelo-update',
  templateUrl: './tarefa-empresa-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaEmpresaModeloUpdateComponent implements OnInit {
  isSaving = false;
  tarefaEmpresaModelo: ITarefaEmpresaModelo | null = null;

  empresaModelosSharedCollection: IEmpresaModelo[] = [];
  servicoContabilsSharedCollection: IServicoContabil[] = [];

  protected tarefaEmpresaModeloService = inject(TarefaEmpresaModeloService);
  protected tarefaEmpresaModeloFormService = inject(TarefaEmpresaModeloFormService);
  protected empresaModeloService = inject(EmpresaModeloService);
  protected servicoContabilService = inject(ServicoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaEmpresaModeloFormGroup = this.tarefaEmpresaModeloFormService.createTarefaEmpresaModeloFormGroup();

  compareEmpresaModelo = (o1: IEmpresaModelo | null, o2: IEmpresaModelo | null): boolean =>
    this.empresaModeloService.compareEmpresaModelo(o1, o2);

  compareServicoContabil = (o1: IServicoContabil | null, o2: IServicoContabil | null): boolean =>
    this.servicoContabilService.compareServicoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefaEmpresaModelo }) => {
      this.tarefaEmpresaModelo = tarefaEmpresaModelo;
      if (tarefaEmpresaModelo) {
        this.updateForm(tarefaEmpresaModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefaEmpresaModelo = this.tarefaEmpresaModeloFormService.getTarefaEmpresaModelo(this.editForm);
    if (tarefaEmpresaModelo.id !== null) {
      this.subscribeToSaveResponse(this.tarefaEmpresaModeloService.update(tarefaEmpresaModelo));
    } else {
      this.subscribeToSaveResponse(this.tarefaEmpresaModeloService.create(tarefaEmpresaModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefaEmpresaModelo>>): void {
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

  protected updateForm(tarefaEmpresaModelo: ITarefaEmpresaModelo): void {
    this.tarefaEmpresaModelo = tarefaEmpresaModelo;
    this.tarefaEmpresaModeloFormService.resetForm(this.editForm, tarefaEmpresaModelo);

    this.empresaModelosSharedCollection = this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
      this.empresaModelosSharedCollection,
      tarefaEmpresaModelo.empresaModelo,
    );
    this.servicoContabilsSharedCollection = this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
      this.servicoContabilsSharedCollection,
      tarefaEmpresaModelo.servicoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaModeloService
      .query()
      .pipe(map((res: HttpResponse<IEmpresaModelo[]>) => res.body ?? []))
      .pipe(
        map((empresaModelos: IEmpresaModelo[]) =>
          this.empresaModeloService.addEmpresaModeloToCollectionIfMissing<IEmpresaModelo>(
            empresaModelos,
            this.tarefaEmpresaModelo?.empresaModelo,
          ),
        ),
      )
      .subscribe((empresaModelos: IEmpresaModelo[]) => (this.empresaModelosSharedCollection = empresaModelos));

    this.servicoContabilService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabil[]>) => res.body ?? []))
      .pipe(
        map((servicoContabils: IServicoContabil[]) =>
          this.servicoContabilService.addServicoContabilToCollectionIfMissing<IServicoContabil>(
            servicoContabils,
            this.tarefaEmpresaModelo?.servicoContabil,
          ),
        ),
      )
      .subscribe((servicoContabils: IServicoContabil[]) => (this.servicoContabilsSharedCollection = servicoContabils));
  }
}
