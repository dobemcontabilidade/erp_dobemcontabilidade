import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { ITarefa } from 'app/entities/tarefa/tarefa.model';
import { TarefaService } from 'app/entities/tarefa/service/tarefa.service';
import { TarefaEmpresaService } from '../service/tarefa-empresa.service';
import { ITarefaEmpresa } from '../tarefa-empresa.model';
import { TarefaEmpresaFormService, TarefaEmpresaFormGroup } from './tarefa-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-empresa-update',
  templateUrl: './tarefa-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  tarefaEmpresa: ITarefaEmpresa | null = null;

  empresasSharedCollection: IEmpresa[] = [];
  contadorsSharedCollection: IContador[] = [];
  tarefasSharedCollection: ITarefa[] = [];

  protected tarefaEmpresaService = inject(TarefaEmpresaService);
  protected tarefaEmpresaFormService = inject(TarefaEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected contadorService = inject(ContadorService);
  protected tarefaService = inject(TarefaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaEmpresaFormGroup = this.tarefaEmpresaFormService.createTarefaEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  compareTarefa = (o1: ITarefa | null, o2: ITarefa | null): boolean => this.tarefaService.compareTarefa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefaEmpresa }) => {
      this.tarefaEmpresa = tarefaEmpresa;
      if (tarefaEmpresa) {
        this.updateForm(tarefaEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefaEmpresa = this.tarefaEmpresaFormService.getTarefaEmpresa(this.editForm);
    if (tarefaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.tarefaEmpresaService.update(tarefaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.tarefaEmpresaService.create(tarefaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefaEmpresa>>): void {
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

  protected updateForm(tarefaEmpresa: ITarefaEmpresa): void {
    this.tarefaEmpresa = tarefaEmpresa;
    this.tarefaEmpresaFormService.resetForm(this.editForm, tarefaEmpresa);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      tarefaEmpresa.empresa,
    );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      tarefaEmpresa.contador,
    );
    this.tarefasSharedCollection = this.tarefaService.addTarefaToCollectionIfMissing<ITarefa>(
      this.tarefasSharedCollection,
      tarefaEmpresa.tarefa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.tarefaEmpresa?.empresa)),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.tarefaEmpresa?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));

    this.tarefaService
      .query()
      .pipe(map((res: HttpResponse<ITarefa[]>) => res.body ?? []))
      .pipe(map((tarefas: ITarefa[]) => this.tarefaService.addTarefaToCollectionIfMissing<ITarefa>(tarefas, this.tarefaEmpresa?.tarefa)))
      .subscribe((tarefas: ITarefa[]) => (this.tarefasSharedCollection = tarefas));
  }
}
