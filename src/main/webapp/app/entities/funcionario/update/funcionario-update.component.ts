import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { SituacaoFuncionarioEnum } from 'app/entities/enumerations/situacao-funcionario-enum.model';
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
  situacaoFuncionarioEnumValues = Object.keys(SituacaoFuncionarioEnum);

  pessoasCollection: IPessoa[] = [];
  empresasSharedCollection: IEmpresa[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected funcionarioService = inject(FuncionarioService);
  protected funcionarioFormService = inject(FuncionarioFormService);
  protected pessoaService = inject(PessoaService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuncionarioFormGroup = this.funcionarioFormService.createFuncionarioFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcionario }) => {
      this.funcionario = funcionario;
      if (funcionario) {
        this.updateForm(funcionario);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('erpDobemcontabilidadeApp.error', { ...err, key: 'error.file.' + err.key }),
        ),
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

    this.pessoasCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(this.pessoasCollection, funcionario.pessoa);
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      funcionario.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query({ 'funcionarioId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.funcionario?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasCollection = pessoas));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.funcionario?.empresa)),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
