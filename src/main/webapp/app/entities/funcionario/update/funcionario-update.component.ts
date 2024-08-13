import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IProfissao } from 'app/entities/profissao/profissao.model';
import { ProfissaoService } from 'app/entities/profissao/service/profissao.service';
import { TipoFuncionarioEnum } from 'app/entities/enumerations/tipo-funcionario-enum.model';
import { FuncionarioService } from '../service/funcionario.service';
import { IFuncionario } from '../funcionario.model';
import { FuncionarioFormService, FuncionarioFormGroup } from './funcionario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-funcionario-update',
  templateUrl: './funcionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  funcionario: IFuncionario | null = null;
  tipoFuncionarioEnumValues = Object.keys(TipoFuncionarioEnum);

  usuarioEmpresasCollection: IUsuarioEmpresa[] = [];
  pessoasSharedCollection: IPessoa[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  profissaosSharedCollection: IProfissao[] = [];

  protected funcionarioService = inject(FuncionarioService);
  protected funcionarioFormService = inject(FuncionarioFormService);
  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected pessoaService = inject(PessoaService);
  protected empresaService = inject(EmpresaService);
  protected profissaoService = inject(ProfissaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuncionarioFormGroup = this.funcionarioFormService.createFuncionarioFormGroup();

  compareUsuarioEmpresa = (o1: IUsuarioEmpresa | null, o2: IUsuarioEmpresa | null): boolean =>
    this.usuarioEmpresaService.compareUsuarioEmpresa(o1, o2);

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareProfissao = (o1: IProfissao | null, o2: IProfissao | null): boolean => this.profissaoService.compareProfissao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcionario }) => {
      this.funcionario = funcionario;
      if (funcionario) {
        this.updateForm(funcionario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcionario = this.funcionarioFormService.getFuncionario(this.editForm);
    if (funcionario.id !== null) {
      this.subscribeToSaveResponse(this.funcionarioService.update(funcionario));
    } else {
      this.subscribeToSaveResponse(this.funcionarioService.create(funcionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionario>>): void {
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

  protected updateForm(funcionario: IFuncionario): void {
    this.funcionario = funcionario;
    this.funcionarioFormService.resetForm(this.editForm, funcionario);

    this.usuarioEmpresasCollection = this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
      this.usuarioEmpresasCollection,
      funcionario.usuarioEmpresa,
    );
    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(
      this.pessoasSharedCollection,
      funcionario.pessoa,
    );
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      funcionario.empresa,
    );
    this.profissaosSharedCollection = this.profissaoService.addProfissaoToCollectionIfMissing<IProfissao>(
      this.profissaosSharedCollection,
      funcionario.profissao,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.usuarioEmpresaService
      .query({ 'funcionarioId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUsuarioEmpresa[]>) => res.body ?? []))
      .pipe(
        map((usuarioEmpresas: IUsuarioEmpresa[]) =>
          this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
            usuarioEmpresas,
            this.funcionario?.usuarioEmpresa,
          ),
        ),
      )
      .subscribe((usuarioEmpresas: IUsuarioEmpresa[]) => (this.usuarioEmpresasCollection = usuarioEmpresas));

    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.funcionario?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.funcionario?.empresa)),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.profissaoService
      .query()
      .pipe(map((res: HttpResponse<IProfissao[]>) => res.body ?? []))
      .pipe(
        map((profissaos: IProfissao[]) =>
          this.profissaoService.addProfissaoToCollectionIfMissing<IProfissao>(profissaos, this.funcionario?.profissao),
        ),
      )
      .subscribe((profissaos: IProfissao[]) => (this.profissaosSharedCollection = profissaos));
  }
}
