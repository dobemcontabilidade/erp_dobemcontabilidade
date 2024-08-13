import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { TipoDependenteFuncionarioEnum } from 'app/entities/enumerations/tipo-dependente-funcionario-enum.model';
import { DependentesFuncionarioService } from '../service/dependentes-funcionario.service';
import { IDependentesFuncionario } from '../dependentes-funcionario.model';
import { DependentesFuncionarioFormService, DependentesFuncionarioFormGroup } from './dependentes-funcionario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dependentes-funcionario-update',
  templateUrl: './dependentes-funcionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DependentesFuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  dependentesFuncionario: IDependentesFuncionario | null = null;
  tipoDependenteFuncionarioEnumValues = Object.keys(TipoDependenteFuncionarioEnum);

  pessoasSharedCollection: IPessoa[] = [];
  funcionariosSharedCollection: IFuncionario[] = [];

  protected dependentesFuncionarioService = inject(DependentesFuncionarioService);
  protected dependentesFuncionarioFormService = inject(DependentesFuncionarioFormService);
  protected pessoaService = inject(PessoaService);
  protected funcionarioService = inject(FuncionarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DependentesFuncionarioFormGroup = this.dependentesFuncionarioFormService.createDependentesFuncionarioFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependentesFuncionario }) => {
      this.dependentesFuncionario = dependentesFuncionario;
      if (dependentesFuncionario) {
        this.updateForm(dependentesFuncionario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dependentesFuncionario = this.dependentesFuncionarioFormService.getDependentesFuncionario(this.editForm);
    if (dependentesFuncionario.id !== null) {
      this.subscribeToSaveResponse(this.dependentesFuncionarioService.update(dependentesFuncionario));
    } else {
      this.subscribeToSaveResponse(this.dependentesFuncionarioService.create(dependentesFuncionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDependentesFuncionario>>): void {
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

  protected updateForm(dependentesFuncionario: IDependentesFuncionario): void {
    this.dependentesFuncionario = dependentesFuncionario;
    this.dependentesFuncionarioFormService.resetForm(this.editForm, dependentesFuncionario);

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(
      this.pessoasSharedCollection,
      dependentesFuncionario.pessoa,
    );
    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      dependentesFuncionario.funcionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(
        map((pessoas: IPessoa[]) =>
          this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.dependentesFuncionario?.pessoa),
        ),
      )
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));

    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.dependentesFuncionario?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
