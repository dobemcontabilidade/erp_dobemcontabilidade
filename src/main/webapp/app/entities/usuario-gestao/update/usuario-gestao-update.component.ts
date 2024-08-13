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
import { IAdministrador } from 'app/entities/administrador/administrador.model';
import { AdministradorService } from 'app/entities/administrador/service/administrador.service';
import { SituacaoUsuarioGestaoEnum } from 'app/entities/enumerations/situacao-usuario-gestao-enum.model';
import { UsuarioGestaoService } from '../service/usuario-gestao.service';
import { IUsuarioGestao } from '../usuario-gestao.model';
import { UsuarioGestaoFormService, UsuarioGestaoFormGroup } from './usuario-gestao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-usuario-gestao-update',
  templateUrl: './usuario-gestao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UsuarioGestaoUpdateComponent implements OnInit {
  isSaving = false;
  usuarioGestao: IUsuarioGestao | null = null;
  situacaoUsuarioGestaoEnumValues = Object.keys(SituacaoUsuarioGestaoEnum);

  administradorsCollection: IAdministrador[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected usuarioGestaoService = inject(UsuarioGestaoService);
  protected usuarioGestaoFormService = inject(UsuarioGestaoFormService);
  protected administradorService = inject(AdministradorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UsuarioGestaoFormGroup = this.usuarioGestaoFormService.createUsuarioGestaoFormGroup();

  compareAdministrador = (o1: IAdministrador | null, o2: IAdministrador | null): boolean =>
    this.administradorService.compareAdministrador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioGestao }) => {
      this.usuarioGestao = usuarioGestao;
      if (usuarioGestao) {
        this.updateForm(usuarioGestao);
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
    const usuarioGestao = this.usuarioGestaoFormService.getUsuarioGestao(this.editForm);
    if (usuarioGestao.id !== null) {
      this.subscribeToSaveResponse(this.usuarioGestaoService.update(usuarioGestao));
    } else {
      this.subscribeToSaveResponse(this.usuarioGestaoService.create(usuarioGestao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioGestao>>): void {
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

  protected updateForm(usuarioGestao: IUsuarioGestao): void {
    this.usuarioGestao = usuarioGestao;
    this.usuarioGestaoFormService.resetForm(this.editForm, usuarioGestao);

    this.administradorsCollection = this.administradorService.addAdministradorToCollectionIfMissing<IAdministrador>(
      this.administradorsCollection,
      usuarioGestao.administrador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.administradorService
      .query({ filter: 'usuariogestao-is-null' })
      .pipe(map((res: HttpResponse<IAdministrador[]>) => res.body ?? []))
      .pipe(
        map((administradors: IAdministrador[]) =>
          this.administradorService.addAdministradorToCollectionIfMissing<IAdministrador>(
            administradors,
            this.usuarioGestao?.administrador,
          ),
        ),
      )
      .subscribe((administradors: IAdministrador[]) => (this.administradorsCollection = administradors));
  }
}
