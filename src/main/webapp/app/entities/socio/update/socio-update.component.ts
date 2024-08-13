import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IProfissao } from 'app/entities/profissao/profissao.model';
import { ProfissaoService } from 'app/entities/profissao/service/profissao.service';
import { FuncaoSocioEnum } from 'app/entities/enumerations/funcao-socio-enum.model';
import { SocioService } from '../service/socio.service';
import { ISocio } from '../socio.model';
import { SocioFormService, SocioFormGroup } from './socio-form.service';

@Component({
  standalone: true,
  selector: 'jhi-socio-update',
  templateUrl: './socio-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SocioUpdateComponent implements OnInit {
  isSaving = false;
  socio: ISocio | null = null;
  funcaoSocioEnumValues = Object.keys(FuncaoSocioEnum);

  pessoasCollection: IPessoa[] = [];
  usuarioEmpresasCollection: IUsuarioEmpresa[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  profissaosSharedCollection: IProfissao[] = [];

  protected socioService = inject(SocioService);
  protected socioFormService = inject(SocioFormService);
  protected pessoaService = inject(PessoaService);
  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected empresaService = inject(EmpresaService);
  protected profissaoService = inject(ProfissaoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SocioFormGroup = this.socioFormService.createSocioFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareUsuarioEmpresa = (o1: IUsuarioEmpresa | null, o2: IUsuarioEmpresa | null): boolean =>
    this.usuarioEmpresaService.compareUsuarioEmpresa(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareProfissao = (o1: IProfissao | null, o2: IProfissao | null): boolean => this.profissaoService.compareProfissao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ socio }) => {
      this.socio = socio;
      if (socio) {
        this.updateForm(socio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const socio = this.socioFormService.getSocio(this.editForm);
    if (socio.id !== null) {
      this.subscribeToSaveResponse(this.socioService.update(socio));
    } else {
      this.subscribeToSaveResponse(this.socioService.create(socio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocio>>): void {
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

  protected updateForm(socio: ISocio): void {
    this.socio = socio;
    this.socioFormService.resetForm(this.editForm, socio);

    this.pessoasCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(this.pessoasCollection, socio.pessoa);
    this.usuarioEmpresasCollection = this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(
      this.usuarioEmpresasCollection,
      socio.usuarioEmpresa,
    );
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      socio.empresa,
    );
    this.profissaosSharedCollection = this.profissaoService.addProfissaoToCollectionIfMissing<IProfissao>(
      this.profissaosSharedCollection,
      socio.profissao,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query({ 'socioId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.socio?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasCollection = pessoas));

    this.usuarioEmpresaService
      .query({ 'socioId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUsuarioEmpresa[]>) => res.body ?? []))
      .pipe(
        map((usuarioEmpresas: IUsuarioEmpresa[]) =>
          this.usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing<IUsuarioEmpresa>(usuarioEmpresas, this.socio?.usuarioEmpresa),
        ),
      )
      .subscribe((usuarioEmpresas: IUsuarioEmpresa[]) => (this.usuarioEmpresasCollection = usuarioEmpresas));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.socio?.empresa)))
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.profissaoService
      .query()
      .pipe(map((res: HttpResponse<IProfissao[]>) => res.body ?? []))
      .pipe(
        map((profissaos: IProfissao[]) =>
          this.profissaoService.addProfissaoToCollectionIfMissing<IProfissao>(profissaos, this.socio?.profissao),
        ),
      )
      .subscribe((profissaos: IProfissao[]) => (this.profissaosSharedCollection = profissaos));
  }
}
