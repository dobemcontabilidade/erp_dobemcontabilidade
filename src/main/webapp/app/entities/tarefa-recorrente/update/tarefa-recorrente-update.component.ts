import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicoContabilAssinaturaEmpresa } from 'app/entities/servico-contabil-assinatura-empresa/servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from 'app/entities/servico-contabil-assinatura-empresa/service/servico-contabil-assinatura-empresa.service';
import { TipoRecorrenciaEnum } from 'app/entities/enumerations/tipo-recorrencia-enum.model';
import { TarefaRecorrenteService } from '../service/tarefa-recorrente.service';
import { ITarefaRecorrente } from '../tarefa-recorrente.model';
import { TarefaRecorrenteFormService, TarefaRecorrenteFormGroup } from './tarefa-recorrente-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-recorrente-update',
  templateUrl: './tarefa-recorrente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaRecorrenteUpdateComponent implements OnInit {
  isSaving = false;
  tarefaRecorrente: ITarefaRecorrente | null = null;
  tipoRecorrenciaEnumValues = Object.keys(TipoRecorrenciaEnum);

  servicoContabilAssinaturaEmpresasSharedCollection: IServicoContabilAssinaturaEmpresa[] = [];

  protected tarefaRecorrenteService = inject(TarefaRecorrenteService);
  protected tarefaRecorrenteFormService = inject(TarefaRecorrenteFormService);
  protected servicoContabilAssinaturaEmpresaService = inject(ServicoContabilAssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaRecorrenteFormGroup = this.tarefaRecorrenteFormService.createTarefaRecorrenteFormGroup();

  compareServicoContabilAssinaturaEmpresa = (
    o1: IServicoContabilAssinaturaEmpresa | null,
    o2: IServicoContabilAssinaturaEmpresa | null,
  ): boolean => this.servicoContabilAssinaturaEmpresaService.compareServicoContabilAssinaturaEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefaRecorrente }) => {
      this.tarefaRecorrente = tarefaRecorrente;
      if (tarefaRecorrente) {
        this.updateForm(tarefaRecorrente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefaRecorrente = this.tarefaRecorrenteFormService.getTarefaRecorrente(this.editForm);
    if (tarefaRecorrente.id !== null) {
      this.subscribeToSaveResponse(this.tarefaRecorrenteService.update(tarefaRecorrente));
    } else {
      this.subscribeToSaveResponse(this.tarefaRecorrenteService.create(tarefaRecorrente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefaRecorrente>>): void {
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

  protected updateForm(tarefaRecorrente: ITarefaRecorrente): void {
    this.tarefaRecorrente = tarefaRecorrente;
    this.tarefaRecorrenteFormService.resetForm(this.editForm, tarefaRecorrente);

    this.servicoContabilAssinaturaEmpresasSharedCollection =
      this.servicoContabilAssinaturaEmpresaService.addServicoContabilAssinaturaEmpresaToCollectionIfMissing<IServicoContabilAssinaturaEmpresa>(
        this.servicoContabilAssinaturaEmpresasSharedCollection,
        tarefaRecorrente.servicoContabilAssinaturaEmpresa,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.servicoContabilAssinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabilAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((servicoContabilAssinaturaEmpresas: IServicoContabilAssinaturaEmpresa[]) =>
          this.servicoContabilAssinaturaEmpresaService.addServicoContabilAssinaturaEmpresaToCollectionIfMissing<IServicoContabilAssinaturaEmpresa>(
            servicoContabilAssinaturaEmpresas,
            this.tarefaRecorrente?.servicoContabilAssinaturaEmpresa,
          ),
        ),
      )
      .subscribe(
        (servicoContabilAssinaturaEmpresas: IServicoContabilAssinaturaEmpresa[]) =>
          (this.servicoContabilAssinaturaEmpresasSharedCollection = servicoContabilAssinaturaEmpresas),
      );
  }
}
