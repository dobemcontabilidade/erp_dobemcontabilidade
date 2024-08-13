import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { SituacaoUsuarioContadorEnum } from 'app/entities/enumerations/situacao-usuario-contador-enum.model';
import { UsuarioContadorService } from '../service/usuario-contador.service';
import { IUsuarioContador } from '../usuario-contador.model';
import { UsuarioContadorFormService, UsuarioContadorFormGroup } from './usuario-contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-usuario-contador-update',
  templateUrl: './usuario-contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UsuarioContadorUpdateComponent implements OnInit {
  isSaving = false;
  usuarioContador: IUsuarioContador | null = null;
  situacaoUsuarioContadorEnumValues = Object.keys(SituacaoUsuarioContadorEnum);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected usuarioContadorService = inject(UsuarioContadorService);
  protected usuarioContadorFormService = inject(UsuarioContadorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UsuarioContadorFormGroup = this.usuarioContadorFormService.createUsuarioContadorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioContador }) => {
      this.usuarioContador = usuarioContador;
      if (usuarioContador) {
        this.updateForm(usuarioContador);
      }
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
    const usuarioContador = this.usuarioContadorFormService.getUsuarioContador(this.editForm);
    if (usuarioContador.id !== null) {
      this.subscribeToSaveResponse(this.usuarioContadorService.update(usuarioContador));
    } else {
      this.subscribeToSaveResponse(this.usuarioContadorService.create(usuarioContador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioContador>>): void {
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

  protected updateForm(usuarioContador: IUsuarioContador): void {
    this.usuarioContador = usuarioContador;
    this.usuarioContadorFormService.resetForm(this.editForm, usuarioContador);
  }
}
