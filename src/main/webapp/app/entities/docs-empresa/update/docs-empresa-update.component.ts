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
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { DocsEmpresaService } from '../service/docs-empresa.service';
import { IDocsEmpresa } from '../docs-empresa.model';
import { DocsEmpresaFormService, DocsEmpresaFormGroup } from './docs-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-docs-empresa-update',
  templateUrl: './docs-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DocsEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  docsEmpresa: IDocsEmpresa | null = null;

  pessoajuridicasSharedCollection: IPessoajuridica[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected docsEmpresaService = inject(DocsEmpresaService);
  protected docsEmpresaFormService = inject(DocsEmpresaFormService);
  protected pessoajuridicaService = inject(PessoajuridicaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DocsEmpresaFormGroup = this.docsEmpresaFormService.createDocsEmpresaFormGroup();

  comparePessoajuridica = (o1: IPessoajuridica | null, o2: IPessoajuridica | null): boolean =>
    this.pessoajuridicaService.comparePessoajuridica(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docsEmpresa }) => {
      this.docsEmpresa = docsEmpresa;
      if (docsEmpresa) {
        this.updateForm(docsEmpresa);
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
    const docsEmpresa = this.docsEmpresaFormService.getDocsEmpresa(this.editForm);
    if (docsEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.docsEmpresaService.update(docsEmpresa));
    } else {
      this.subscribeToSaveResponse(this.docsEmpresaService.create(docsEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocsEmpresa>>): void {
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

  protected updateForm(docsEmpresa: IDocsEmpresa): void {
    this.docsEmpresa = docsEmpresa;
    this.docsEmpresaFormService.resetForm(this.editForm, docsEmpresa);

    this.pessoajuridicasSharedCollection = this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
      this.pessoajuridicasSharedCollection,
      docsEmpresa.pessoaJuridica,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoajuridicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoajuridica[]>) => res.body ?? []))
      .pipe(
        map((pessoajuridicas: IPessoajuridica[]) =>
          this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
            pessoajuridicas,
            this.docsEmpresa?.pessoaJuridica,
          ),
        ),
      )
      .subscribe((pessoajuridicas: IPessoajuridica[]) => (this.pessoajuridicasSharedCollection = pessoajuridicas));
  }
}
