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
import { AnexoPessoaService } from '../service/anexo-pessoa.service';
import { IAnexoPessoa } from '../anexo-pessoa.model';
import { AnexoPessoaFormService, AnexoPessoaFormGroup } from './anexo-pessoa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-pessoa-update',
  templateUrl: './anexo-pessoa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoPessoaUpdateComponent implements OnInit {
  isSaving = false;
  anexoPessoa: IAnexoPessoa | null = null;

  pessoasSharedCollection: IPessoa[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected anexoPessoaService = inject(AnexoPessoaService);
  protected anexoPessoaFormService = inject(AnexoPessoaFormService);
  protected pessoaService = inject(PessoaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoPessoaFormGroup = this.anexoPessoaFormService.createAnexoPessoaFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoPessoa }) => {
      this.anexoPessoa = anexoPessoa;
      if (anexoPessoa) {
        this.updateForm(anexoPessoa);
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
    const anexoPessoa = this.anexoPessoaFormService.getAnexoPessoa(this.editForm);
    if (anexoPessoa.id !== null) {
      this.subscribeToSaveResponse(this.anexoPessoaService.update(anexoPessoa));
    } else {
      this.subscribeToSaveResponse(this.anexoPessoaService.create(anexoPessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoPessoa>>): void {
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

  protected updateForm(anexoPessoa: IAnexoPessoa): void {
    this.anexoPessoa = anexoPessoa;
    this.anexoPessoaFormService.resetForm(this.editForm, anexoPessoa);

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(
      this.pessoasSharedCollection,
      anexoPessoa.pessoa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.anexoPessoa?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));
  }
}
