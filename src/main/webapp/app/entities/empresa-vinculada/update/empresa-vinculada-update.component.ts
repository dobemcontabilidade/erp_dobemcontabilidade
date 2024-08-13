import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { RegimePrevidenciarioEnum } from 'app/entities/enumerations/regime-previdenciario-enum.model';
import { UnidadePagamentoSalarioEnum } from 'app/entities/enumerations/unidade-pagamento-salario-enum.model';
import { JornadaEspecialEnum } from 'app/entities/enumerations/jornada-especial-enum.model';
import { TipoInscricaoEmpresaVinculadaEnum } from 'app/entities/enumerations/tipo-inscricao-empresa-vinculada-enum.model';
import { TipoContratoTrabalhoEnum } from 'app/entities/enumerations/tipo-contrato-trabalho-enum.model';
import { TipoRegimeTrabalhoEnum } from 'app/entities/enumerations/tipo-regime-trabalho-enum.model';
import { DiasDaSemanaEnum } from 'app/entities/enumerations/dias-da-semana-enum.model';
import { TipoJornadaEmpresaVinculadaEnum } from 'app/entities/enumerations/tipo-jornada-empresa-vinculada-enum.model';
import { EmpresaVinculadaService } from '../service/empresa-vinculada.service';
import { IEmpresaVinculada } from '../empresa-vinculada.model';
import { EmpresaVinculadaFormService, EmpresaVinculadaFormGroup } from './empresa-vinculada-form.service';

@Component({
  standalone: true,
  selector: 'jhi-empresa-vinculada-update',
  templateUrl: './empresa-vinculada-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmpresaVinculadaUpdateComponent implements OnInit {
  isSaving = false;
  empresaVinculada: IEmpresaVinculada | null = null;
  regimePrevidenciarioEnumValues = Object.keys(RegimePrevidenciarioEnum);
  unidadePagamentoSalarioEnumValues = Object.keys(UnidadePagamentoSalarioEnum);
  jornadaEspecialEnumValues = Object.keys(JornadaEspecialEnum);
  tipoInscricaoEmpresaVinculadaEnumValues = Object.keys(TipoInscricaoEmpresaVinculadaEnum);
  tipoContratoTrabalhoEnumValues = Object.keys(TipoContratoTrabalhoEnum);
  tipoRegimeTrabalhoEnumValues = Object.keys(TipoRegimeTrabalhoEnum);
  diasDaSemanaEnumValues = Object.keys(DiasDaSemanaEnum);
  tipoJornadaEmpresaVinculadaEnumValues = Object.keys(TipoJornadaEmpresaVinculadaEnum);

  funcionariosSharedCollection: IFuncionario[] = [];

  protected empresaVinculadaService = inject(EmpresaVinculadaService);
  protected empresaVinculadaFormService = inject(EmpresaVinculadaFormService);
  protected funcionarioService = inject(FuncionarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpresaVinculadaFormGroup = this.empresaVinculadaFormService.createEmpresaVinculadaFormGroup();

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresaVinculada }) => {
      this.empresaVinculada = empresaVinculada;
      if (empresaVinculada) {
        this.updateForm(empresaVinculada);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empresaVinculada = this.empresaVinculadaFormService.getEmpresaVinculada(this.editForm);
    if (empresaVinculada.id !== null) {
      this.subscribeToSaveResponse(this.empresaVinculadaService.update(empresaVinculada));
    } else {
      this.subscribeToSaveResponse(this.empresaVinculadaService.create(empresaVinculada));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresaVinculada>>): void {
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

  protected updateForm(empresaVinculada: IEmpresaVinculada): void {
    this.empresaVinculada = empresaVinculada;
    this.empresaVinculadaFormService.resetForm(this.editForm, empresaVinculada);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      empresaVinculada.funcionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.empresaVinculada?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
