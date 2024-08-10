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
import { SituacaoUsuarioErpEnum } from 'app/entities/enumerations/situacao-usuario-erp-enum.model';
import { UsuarioErpService } from '../service/usuario-erp.service';
import { IUsuarioErp } from '../usuario-erp.model';
import { UsuarioErpFormService, UsuarioErpFormGroup } from './usuario-erp-form.service';

@Component({
  standalone: true,
  selector: 'jhi-usuario-erp-update',
  templateUrl: './usuario-erp-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UsuarioErpUpdateComponent implements OnInit {
  isSaving = false;
  usuarioErp: IUsuarioErp | null = null;
  situacaoUsuarioErpEnumValues = Object.keys(SituacaoUsuarioErpEnum);

  administradorsCollection: IAdministrador[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected usuarioErpService = inject(UsuarioErpService);
  protected usuarioErpFormService = inject(UsuarioErpFormService);
  protected administradorService = inject(AdministradorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UsuarioErpFormGroup = this.usuarioErpFormService.createUsuarioErpFormGroup();

  compareAdministrador = (o1: IAdministrador | null, o2: IAdministrador | null): boolean =>
    this.administradorService.compareAdministrador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioErp }) => {
      this.usuarioErp = usuarioErp;
      if (usuarioErp) {
        this.updateForm(usuarioErp);
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
    const usuarioErp = this.usuarioErpFormService.getUsuarioErp(this.editForm);
    if (usuarioErp.id !== null) {
      this.subscribeToSaveResponse(this.usuarioErpService.update(usuarioErp));
    } else {
      this.subscribeToSaveResponse(this.usuarioErpService.create(usuarioErp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioErp>>): void {
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

  protected updateForm(usuarioErp: IUsuarioErp): void {
    this.usuarioErp = usuarioErp;
    this.usuarioErpFormService.resetForm(this.editForm, usuarioErp);

    this.administradorsCollection = this.administradorService.addAdministradorToCollectionIfMissing<IAdministrador>(
      this.administradorsCollection,
      usuarioErp.administrador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.administradorService
      .query({ filter: 'usuarioerp-is-null' })
      .pipe(map((res: HttpResponse<IAdministrador[]>) => res.body ?? []))
      .pipe(
        map((administradors: IAdministrador[]) =>
          this.administradorService.addAdministradorToCollectionIfMissing<IAdministrador>(administradors, this.usuarioErp?.administrador),
        ),
      )
      .subscribe((administradors: IAdministrador[]) => (this.administradorsCollection = administradors));
  }
}
