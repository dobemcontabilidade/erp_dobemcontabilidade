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
import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { DocsPessoaService } from '../service/docs-pessoa.service';
import { IDocsPessoa } from '../docs-pessoa.model';
import { DocsPessoaFormService, DocsPessoaFormGroup } from './docs-pessoa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-docs-pessoa-update',
  templateUrl: './docs-pessoa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DocsPessoaUpdateComponent implements OnInit {
  isSaving = false;
  docsPessoa: IDocsPessoa | null = null;

  pessoaFisicasSharedCollection: IPessoaFisica[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected docsPessoaService = inject(DocsPessoaService);
  protected docsPessoaFormService = inject(DocsPessoaFormService);
  protected pessoaFisicaService = inject(PessoaFisicaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DocsPessoaFormGroup = this.docsPessoaFormService.createDocsPessoaFormGroup();

  comparePessoaFisica = (o1: IPessoaFisica | null, o2: IPessoaFisica | null): boolean =>
    this.pessoaFisicaService.comparePessoaFisica(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docsPessoa }) => {
      this.docsPessoa = docsPessoa;
      if (docsPessoa) {
        this.updateForm(docsPessoa);
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
    const docsPessoa = this.docsPessoaFormService.getDocsPessoa(this.editForm);
    if (docsPessoa.id !== null) {
      this.subscribeToSaveResponse(this.docsPessoaService.update(docsPessoa));
    } else {
      this.subscribeToSaveResponse(this.docsPessoaService.create(docsPessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocsPessoa>>): void {
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

  protected updateForm(docsPessoa: IDocsPessoa): void {
    this.docsPessoa = docsPessoa;
    this.docsPessoaFormService.resetForm(this.editForm, docsPessoa);

    this.pessoaFisicasSharedCollection = this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(
      this.pessoaFisicasSharedCollection,
      docsPessoa.pessoa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaFisicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoaFisica[]>) => res.body ?? []))
      .pipe(
        map((pessoaFisicas: IPessoaFisica[]) =>
          this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(pessoaFisicas, this.docsPessoa?.pessoa),
        ),
      )
      .subscribe((pessoaFisicas: IPessoaFisica[]) => (this.pessoaFisicasSharedCollection = pessoaFisicas));
  }
}
