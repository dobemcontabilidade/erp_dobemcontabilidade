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
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { SituacaoUsuarioEmpresaEnum } from 'app/entities/enumerations/situacao-usuario-empresa-enum.model';
import { TipoUsuarioEmpresaEnum } from 'app/entities/enumerations/tipo-usuario-empresa-enum.model';
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
  tipoUsuarioEmpresaEnumValues = Object.keys(TipoUsuarioEmpresaEnum);

  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected usuarioEmpresaFormService = inject(UsuarioEmpresaFormService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UsuarioEmpresaFormGroup = this.usuarioEmpresaFormService.createUsuarioEmpresaFormGroup();

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

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

    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      usuarioEmpresa.assinaturaEmpresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.usuarioEmpresa?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));
  }
}
