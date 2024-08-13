import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFluxoExecucao } from '../fluxo-execucao.model';
import { FluxoExecucaoService } from '../service/fluxo-execucao.service';
import { FluxoExecucaoFormService, FluxoExecucaoFormGroup } from './fluxo-execucao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-fluxo-execucao-update',
  templateUrl: './fluxo-execucao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FluxoExecucaoUpdateComponent implements OnInit {
  isSaving = false;
  fluxoExecucao: IFluxoExecucao | null = null;

  protected fluxoExecucaoService = inject(FluxoExecucaoService);
  protected fluxoExecucaoFormService = inject(FluxoExecucaoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FluxoExecucaoFormGroup = this.fluxoExecucaoFormService.createFluxoExecucaoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fluxoExecucao }) => {
      this.fluxoExecucao = fluxoExecucao;
      if (fluxoExecucao) {
        this.updateForm(fluxoExecucao);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fluxoExecucao = this.fluxoExecucaoFormService.getFluxoExecucao(this.editForm);
    if (fluxoExecucao.id !== null) {
      this.subscribeToSaveResponse(this.fluxoExecucaoService.update(fluxoExecucao));
    } else {
      this.subscribeToSaveResponse(this.fluxoExecucaoService.create(fluxoExecucao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFluxoExecucao>>): void {
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

  protected updateForm(fluxoExecucao: IFluxoExecucao): void {
    this.fluxoExecucao = fluxoExecucao;
    this.fluxoExecucaoFormService.resetForm(this.editForm, fluxoExecucao);
  }
}
