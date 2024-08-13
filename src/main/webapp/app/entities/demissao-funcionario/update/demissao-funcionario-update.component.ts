import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { AvisoPrevioEnum } from 'app/entities/enumerations/aviso-previo-enum.model';
import { SituacaoDemissaoEnum } from 'app/entities/enumerations/situacao-demissao-enum.model';
import { TipoDemissaoEnum } from 'app/entities/enumerations/tipo-demissao-enum.model';
import { DemissaoFuncionarioService } from '../service/demissao-funcionario.service';
import { IDemissaoFuncionario } from '../demissao-funcionario.model';
import { DemissaoFuncionarioFormService, DemissaoFuncionarioFormGroup } from './demissao-funcionario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-demissao-funcionario-update',
  templateUrl: './demissao-funcionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DemissaoFuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  demissaoFuncionario: IDemissaoFuncionario | null = null;
  avisoPrevioEnumValues = Object.keys(AvisoPrevioEnum);
  situacaoDemissaoEnumValues = Object.keys(SituacaoDemissaoEnum);
  tipoDemissaoEnumValues = Object.keys(TipoDemissaoEnum);

  funcionariosSharedCollection: IFuncionario[] = [];

  protected demissaoFuncionarioService = inject(DemissaoFuncionarioService);
  protected demissaoFuncionarioFormService = inject(DemissaoFuncionarioFormService);
  protected funcionarioService = inject(FuncionarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DemissaoFuncionarioFormGroup = this.demissaoFuncionarioFormService.createDemissaoFuncionarioFormGroup();

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demissaoFuncionario }) => {
      this.demissaoFuncionario = demissaoFuncionario;
      if (demissaoFuncionario) {
        this.updateForm(demissaoFuncionario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demissaoFuncionario = this.demissaoFuncionarioFormService.getDemissaoFuncionario(this.editForm);
    if (demissaoFuncionario.id !== null) {
      this.subscribeToSaveResponse(this.demissaoFuncionarioService.update(demissaoFuncionario));
    } else {
      this.subscribeToSaveResponse(this.demissaoFuncionarioService.create(demissaoFuncionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemissaoFuncionario>>): void {
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

  protected updateForm(demissaoFuncionario: IDemissaoFuncionario): void {
    this.demissaoFuncionario = demissaoFuncionario;
    this.demissaoFuncionarioFormService.resetForm(this.editForm, demissaoFuncionario);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      demissaoFuncionario.funcionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.demissaoFuncionario?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
