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
import { DepartamentoService } from '../service/departamento.service';
import { IDepartamento } from '../departamento.model';
import { DepartamentoFormService, DepartamentoFormGroup } from './departamento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-departamento-update',
  templateUrl: './departamento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepartamentoUpdateComponent implements OnInit {
  isSaving = false;
  departamento: IDepartamento | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected departamentoService = inject(DepartamentoService);
  protected departamentoFormService = inject(DepartamentoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DepartamentoFormGroup = this.departamentoFormService.createDepartamentoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departamento }) => {
      this.departamento = departamento;
      if (departamento) {
        this.updateForm(departamento);
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
    const departamento = this.departamentoFormService.getDepartamento(this.editForm);
    if (departamento.id !== null) {
      this.subscribeToSaveResponse(this.departamentoService.update(departamento));
    } else {
      this.subscribeToSaveResponse(this.departamentoService.create(departamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamento>>): void {
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

  protected updateForm(departamento: IDepartamento): void {
    this.departamento = departamento;
    this.departamentoFormService.resetForm(this.editForm, departamento);
  }
}
