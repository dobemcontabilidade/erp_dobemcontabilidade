import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicoContabilOrdemServico } from 'app/entities/servico-contabil-ordem-servico/servico-contabil-ordem-servico.model';
import { ServicoContabilOrdemServicoService } from 'app/entities/servico-contabil-ordem-servico/service/servico-contabil-ordem-servico.service';
import { ITarefaOrdemServico } from '../tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from '../service/tarefa-ordem-servico.service';
import { TarefaOrdemServicoFormService, TarefaOrdemServicoFormGroup } from './tarefa-ordem-servico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-ordem-servico-update',
  templateUrl: './tarefa-ordem-servico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TarefaOrdemServicoUpdateComponent implements OnInit {
  isSaving = false;
  tarefaOrdemServico: ITarefaOrdemServico | null = null;

  servicoContabilOrdemServicosSharedCollection: IServicoContabilOrdemServico[] = [];

  protected tarefaOrdemServicoService = inject(TarefaOrdemServicoService);
  protected tarefaOrdemServicoFormService = inject(TarefaOrdemServicoFormService);
  protected servicoContabilOrdemServicoService = inject(ServicoContabilOrdemServicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TarefaOrdemServicoFormGroup = this.tarefaOrdemServicoFormService.createTarefaOrdemServicoFormGroup();

  compareServicoContabilOrdemServico = (o1: IServicoContabilOrdemServico | null, o2: IServicoContabilOrdemServico | null): boolean =>
    this.servicoContabilOrdemServicoService.compareServicoContabilOrdemServico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarefaOrdemServico }) => {
      this.tarefaOrdemServico = tarefaOrdemServico;
      if (tarefaOrdemServico) {
        this.updateForm(tarefaOrdemServico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarefaOrdemServico = this.tarefaOrdemServicoFormService.getTarefaOrdemServico(this.editForm);
    if (tarefaOrdemServico.id !== null) {
      this.subscribeToSaveResponse(this.tarefaOrdemServicoService.update(tarefaOrdemServico));
    } else {
      this.subscribeToSaveResponse(this.tarefaOrdemServicoService.create(tarefaOrdemServico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefaOrdemServico>>): void {
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

  protected updateForm(tarefaOrdemServico: ITarefaOrdemServico): void {
    this.tarefaOrdemServico = tarefaOrdemServico;
    this.tarefaOrdemServicoFormService.resetForm(this.editForm, tarefaOrdemServico);

    this.servicoContabilOrdemServicosSharedCollection =
      this.servicoContabilOrdemServicoService.addServicoContabilOrdemServicoToCollectionIfMissing<IServicoContabilOrdemServico>(
        this.servicoContabilOrdemServicosSharedCollection,
        tarefaOrdemServico.servicoContabilOrdemServico,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.servicoContabilOrdemServicoService
      .query()
      .pipe(map((res: HttpResponse<IServicoContabilOrdemServico[]>) => res.body ?? []))
      .pipe(
        map((servicoContabilOrdemServicos: IServicoContabilOrdemServico[]) =>
          this.servicoContabilOrdemServicoService.addServicoContabilOrdemServicoToCollectionIfMissing<IServicoContabilOrdemServico>(
            servicoContabilOrdemServicos,
            this.tarefaOrdemServico?.servicoContabilOrdemServico,
          ),
        ),
      )
      .subscribe(
        (servicoContabilOrdemServicos: IServicoContabilOrdemServico[]) =>
          (this.servicoContabilOrdemServicosSharedCollection = servicoContabilOrdemServicos),
      );
  }
}
