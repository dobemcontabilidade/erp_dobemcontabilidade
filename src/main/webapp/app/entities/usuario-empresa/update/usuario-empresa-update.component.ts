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
import { SituacaoUsuarioEmpresaEnum } from 'app/entities/enumerations/situacao-usuario-empresa-enum.model';
import { UsuarioEmpresaService } from '../service/usuario-empresa.service';
import { IUsuarioEmpresa } from '../usuario-empresa.model';
import { UsuarioEmpresaFormService, UsuarioEmpresaFormGroup } from './usuario-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-usuario-empresa-update',
  templateUrl: './usuario-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UsuarioEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  usuarioEmpresa: IUsuarioEmpresa | null = null;
  situacaoUsuarioEmpresaEnumValues = Object.keys(SituacaoUsuarioEmpresaEnum);

  pessoasCollection: IPessoa[] = [];
  empresasSharedCollection: IEmpresa[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected usuarioEmpresaFormService = inject(UsuarioEmpresaFormService);
  protected pessoaService = inject(PessoaService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UsuarioEmpresaFormGroup = this.usuarioEmpresaFormService.createUsuarioEmpresaFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioEmpresa }) => {
      this.usuarioEmpresa = usuarioEmpresa;
      if (usuarioEmpresa) {
        this.updateForm(usuarioEmpresa);
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
    const usuarioEmpresa = this.usuarioEmpresaFormService.getUsuarioEmpresa(this.editForm);
    if (usuarioEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.usuarioEmpresaService.update(usuarioEmpresa));
    } else {
      this.subscribeToSaveResponse(this.usuarioEmpresaService.create(usuarioEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioEmpresa>>): void {
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

  protected updateForm(usuarioEmpresa: IUsuarioEmpresa): void {
    this.usuarioEmpresa = usuarioEmpresa;
    this.usuarioEmpresaFormService.resetForm(this.editForm, usuarioEmpresa);

    this.pessoasCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(this.pessoasCollection, usuarioEmpresa.pessoa);
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      usuarioEmpresa.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query({ 'usuarioEmpresaId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.usuarioEmpresa?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasCollection = pessoas));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.usuarioEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
