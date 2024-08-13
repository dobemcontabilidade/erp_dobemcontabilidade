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
import { IFluxoModelo } from 'app/entities/fluxo-modelo/fluxo-modelo.model';
import { FluxoModeloService } from 'app/entities/fluxo-modelo/service/fluxo-modelo.service';
import { StatusDaOSEnum } from 'app/entities/enumerations/status-da-os-enum.model';
import { OrdemServicoService } from '../service/ordem-servico.service';
import { IOrdemServico } from '../ordem-servico.model';
import { OrdemServicoFormService, OrdemServicoFormGroup } from './ordem-servico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-ordem-servico-update',
  templateUrl: './ordem-servico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrdemServicoUpdateComponent implements OnInit {
  isSaving = false;
  ordemServico: IOrdemServico | null = null;
  statusDaOSEnumValues = Object.keys(StatusDaOSEnum);

  empresasSharedCollection: IEmpresa[] = [];
  contadorsSharedCollection: IContador[] = [];
  fluxoModelosSharedCollection: IFluxoModelo[] = [];

  protected ordemServicoService = inject(OrdemServicoService);
  protected ordemServicoFormService = inject(OrdemServicoFormService);
  protected empresaService = inject(EmpresaService);
  protected contadorService = inject(ContadorService);
  protected fluxoModeloService = inject(FluxoModeloService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrdemServicoFormGroup = this.ordemServicoFormService.createOrdemServicoFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareContador = (o1: IContador | null, o2: IContador | null): boolean => this.contadorService.compareContador(o1, o2);

  compareFluxoModelo = (o1: IFluxoModelo | null, o2: IFluxoModelo | null): boolean => this.fluxoModeloService.compareFluxoModelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ordemServico }) => {
      this.ordemServico = ordemServico;
      if (ordemServico) {
        this.updateForm(ordemServico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ordemServico = this.ordemServicoFormService.getOrdemServico(this.editForm);
    if (ordemServico.id !== null) {
      this.subscribeToSaveResponse(this.ordemServicoService.update(ordemServico));
    } else {
      this.subscribeToSaveResponse(this.ordemServicoService.create(ordemServico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdemServico>>): void {
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

  protected updateForm(ordemServico: IOrdemServico): void {
    this.ordemServico = ordemServico;
    this.ordemServicoFormService.resetForm(this.editForm, ordemServico);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      ordemServico.empresa,
    );
    this.contadorsSharedCollection = this.contadorService.addContadorToCollectionIfMissing<IContador>(
      this.contadorsSharedCollection,
      ordemServico.contador,
    );
    this.fluxoModelosSharedCollection = this.fluxoModeloService.addFluxoModeloToCollectionIfMissing<IFluxoModelo>(
      this.fluxoModelosSharedCollection,
      ordemServico.fluxoModelo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.ordemServico?.empresa)),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.contadorService
      .query()
      .pipe(map((res: HttpResponse<IContador[]>) => res.body ?? []))
      .pipe(
        map((contadors: IContador[]) =>
          this.contadorService.addContadorToCollectionIfMissing<IContador>(contadors, this.ordemServico?.contador),
        ),
      )
      .subscribe((contadors: IContador[]) => (this.contadorsSharedCollection = contadors));

    this.fluxoModeloService
      .query()
      .pipe(map((res: HttpResponse<IFluxoModelo[]>) => res.body ?? []))
      .pipe(
        map((fluxoModelos: IFluxoModelo[]) =>
          this.fluxoModeloService.addFluxoModeloToCollectionIfMissing<IFluxoModelo>(fluxoModelos, this.ordemServico?.fluxoModelo),
        ),
      )
      .subscribe((fluxoModelos: IFluxoModelo[]) => (this.fluxoModelosSharedCollection = fluxoModelos));
  }
}
