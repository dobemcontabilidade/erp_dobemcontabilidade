import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { ITarefaOrdemServico } from 'app/entities/tarefa-ordem-servico/tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from 'app/entities/tarefa-ordem-servico/service/tarefa-ordem-servico.service';
import { AnexoRequeridoTarefaOrdemServicoService } from '../service/anexo-requerido-tarefa-ordem-servico.service';
import { IAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';
import {
  AnexoRequeridoTarefaOrdemServicoFormService,
  AnexoRequeridoTarefaOrdemServicoFormGroup,
} from './anexo-requerido-tarefa-ordem-servico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-tarefa-ordem-servico-update',
  templateUrl: './anexo-requerido-tarefa-ordem-servico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoRequeridoTarefaOrdemServicoUpdateComponent implements OnInit {
  isSaving = false;
  anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico | null = null;

  anexoRequeridosSharedCollection: IAnexoRequerido[] = [];
  tarefaOrdemServicosSharedCollection: ITarefaOrdemServico[] = [];

  protected anexoRequeridoTarefaOrdemServicoService = inject(AnexoRequeridoTarefaOrdemServicoService);
  protected anexoRequeridoTarefaOrdemServicoFormService = inject(AnexoRequeridoTarefaOrdemServicoFormService);
  protected anexoRequeridoService = inject(AnexoRequeridoService);
  protected tarefaOrdemServicoService = inject(TarefaOrdemServicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoRequeridoTarefaOrdemServicoFormGroup =
    this.anexoRequeridoTarefaOrdemServicoFormService.createAnexoRequeridoTarefaOrdemServicoFormGroup();

  compareAnexoRequerido = (o1: IAnexoRequerido | null, o2: IAnexoRequerido | null): boolean =>
    this.anexoRequeridoService.compareAnexoRequerido(o1, o2);

  compareTarefaOrdemServico = (o1: ITarefaOrdemServico | null, o2: ITarefaOrdemServico | null): boolean =>
    this.tarefaOrdemServicoService.compareTarefaOrdemServico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoRequeridoTarefaOrdemServico }) => {
      this.anexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServico;
      if (anexoRequeridoTarefaOrdemServico) {
        this.updateForm(anexoRequeridoTarefaOrdemServico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexoRequeridoTarefaOrdemServico = this.anexoRequeridoTarefaOrdemServicoFormService.getAnexoRequeridoTarefaOrdemServico(
      this.editForm,
    );
    if (anexoRequeridoTarefaOrdemServico.id !== null) {
      this.subscribeToSaveResponse(this.anexoRequeridoTarefaOrdemServicoService.update(anexoRequeridoTarefaOrdemServico));
    } else {
      this.subscribeToSaveResponse(this.anexoRequeridoTarefaOrdemServicoService.create(anexoRequeridoTarefaOrdemServico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoRequeridoTarefaOrdemServico>>): void {
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

  protected updateForm(anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico): void {
    this.anexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServico;
    this.anexoRequeridoTarefaOrdemServicoFormService.resetForm(this.editForm, anexoRequeridoTarefaOrdemServico);

    this.anexoRequeridosSharedCollection = this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
      this.anexoRequeridosSharedCollection,
      anexoRequeridoTarefaOrdemServico.anexoRequerido,
    );
    this.tarefaOrdemServicosSharedCollection =
      this.tarefaOrdemServicoService.addTarefaOrdemServicoToCollectionIfMissing<ITarefaOrdemServico>(
        this.tarefaOrdemServicosSharedCollection,
        anexoRequeridoTarefaOrdemServico.tarefaOrdemServico,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.anexoRequeridoService
      .query()
      .pipe(map((res: HttpResponse<IAnexoRequerido[]>) => res.body ?? []))
      .pipe(
        map((anexoRequeridos: IAnexoRequerido[]) =>
          this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
            anexoRequeridos,
            this.anexoRequeridoTarefaOrdemServico?.anexoRequerido,
          ),
        ),
      )
      .subscribe((anexoRequeridos: IAnexoRequerido[]) => (this.anexoRequeridosSharedCollection = anexoRequeridos));

    this.tarefaOrdemServicoService
      .query()
      .pipe(map((res: HttpResponse<ITarefaOrdemServico[]>) => res.body ?? []))
      .pipe(
        map((tarefaOrdemServicos: ITarefaOrdemServico[]) =>
          this.tarefaOrdemServicoService.addTarefaOrdemServicoToCollectionIfMissing<ITarefaOrdemServico>(
            tarefaOrdemServicos,
            this.anexoRequeridoTarefaOrdemServico?.tarefaOrdemServico,
          ),
        ),
      )
      .subscribe((tarefaOrdemServicos: ITarefaOrdemServico[]) => (this.tarefaOrdemServicosSharedCollection = tarefaOrdemServicos));
  }
}
