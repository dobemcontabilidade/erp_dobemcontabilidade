import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { ContadorResponsavelOrdemServicoService } from '../service/contador-responsavel-ordem-servico.service';
import { IContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';
import {
  ContadorResponsavelOrdemServicoFormService,
  ContadorResponsavelOrdemServicoFormGroup,
} from './contador-responsavel-ordem-servico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contador-responsavel-ordem-servico-update',
  templateUrl: './contador-responsavel-ordem-servico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContadorResponsavelOrdemServicoUpdateComponent implements OnInit {
  isSaving = false;
  contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico | null = null;

  tarefaOrdemServicoExecucaosSharedCollection: ITarefaOrdemServicoExecucao[] = [];
  contadorsSharedCollection: IContador[] = [];

  protected contadorResponsavelOrdemServicoService = inject(ContadorResponsavelOrdemServicoService);
  protected contadorResponsavelOrdemServicoFormService = inject(ContadorResponsavelOrdemServicoFormService);
  protected tarefaOrdemServicoExecucaoService = inject(TarefaOrdemServicoExecucaoService);
  protected contadorService = inject(ContadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContadorResponsavelOrdemServicoFormGroup =
    this.contadorResponsavelOrdemServicoFormService.createContadorResponsavelOrdemServicoFormGroup();

  compareTarefaOrdemServicoExecucao = (o1: ITarefaOrdemServicoExecucao | null, o2: ITarefaOrdemServicoExecucao | null): boolean =>
    this.tarefaOrdemServicoExecucaoService.compareTarefaOrdemServicoExecucao(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contadorResponsavelOrdemServico }) => {
      this.contadorResponsavelOrdemServico = contadorResponsavelOrdemServico;
      if (contadorResponsavelOrdemServico) {
        this.updateForm(contadorResponsavelOrdemServico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contadorResponsavelOrdemServico = this.contadorResponsavelOrdemServicoFormService.getContadorResponsavelOrdemServico(
      this.editForm,
    );
    if (contadorResponsavelOrdemServico.id !== null) {
      this.subscribeToSaveResponse(this.contadorResponsavelOrdemServicoService.update(contadorResponsavelOrdemServico));
    } else {
      this.subscribeToSaveResponse(this.contadorResponsavelOrdemServicoService.create(contadorResponsavelOrdemServico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContadorResponsavelOrdemServico>>): void {
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

  protected updateForm(contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico): void {
    this.contadorResponsavelOrdemServico = contadorResponsavelOrdemServico;
    this.contadorResponsavelOrdemServicoFormService.resetForm(this.editForm, contadorResponsavelOrdemServico);

    this.tarefaOrdemServicoExecucaosSharedCollection =
      this.tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing<ITarefaOrdemServicoExecucao>(
        this.tarefaOrdemServicoExecucaosSharedCollection,
        contadorResponsavelOrdemServico.tarefaOrdemServicoExecucao,
      );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      contadorResponsavelOrdemServico.contador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tarefaOrdemServicoExecucaoService
      .query()
      .pipe(map((res: HttpResponse<ITarefaOrdemServicoExecucao[]>) => res.body ?? []))
      .pipe(
        map((tarefaOrdemServicoExecucaos: ITarefaOrdemServicoExecucao[]) =>
          this.tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing<ITarefaOrdemServicoExecucao>(
            tarefaOrdemServicoExecucaos,
            this.contadorResponsavelOrdemServico?.tarefaOrdemServicoExecucao,
          ),
        ),
      )
      .subscribe(
        (tarefaOrdemServicoExecucaos: ITarefaOrdemServicoExecucao[]) =>
          (this.tarefaOrdemServicoExecucaosSharedCollection = tarefaOrdemServicoExecucaos),
      );

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.contadorResponsavelOrdemServico?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));
  }
}
