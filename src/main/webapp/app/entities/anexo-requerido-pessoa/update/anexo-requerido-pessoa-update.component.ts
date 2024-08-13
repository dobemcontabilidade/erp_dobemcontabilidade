import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAnexoPessoa } from 'app/entities/anexo-pessoa/anexo-pessoa.model';
import { AnexoPessoaService } from 'app/entities/anexo-pessoa/service/anexo-pessoa.service';
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { TipoAnexoPessoaEnum } from 'app/entities/enumerations/tipo-anexo-pessoa-enum.model';
import { AnexoRequeridoPessoaService } from '../service/anexo-requerido-pessoa.service';
import { IAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';
import { AnexoRequeridoPessoaFormService, AnexoRequeridoPessoaFormGroup } from './anexo-requerido-pessoa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-pessoa-update',
  templateUrl: './anexo-requerido-pessoa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AnexoRequeridoPessoaUpdateComponent implements OnInit {
  isSaving = false;
  anexoRequeridoPessoa: IAnexoRequeridoPessoa | null = null;
  tipoAnexoPessoaEnumValues = Object.keys(TipoAnexoPessoaEnum);

  anexoPessoasSharedCollection: IAnexoPessoa[] = [];
  anexoRequeridosSharedCollection: IAnexoRequerido[] = [];

  protected anexoRequeridoPessoaService = inject(AnexoRequeridoPessoaService);
  protected anexoRequeridoPessoaFormService = inject(AnexoRequeridoPessoaFormService);
  protected anexoPessoaService = inject(AnexoPessoaService);
  protected anexoRequeridoService = inject(AnexoRequeridoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AnexoRequeridoPessoaFormGroup = this.anexoRequeridoPessoaFormService.createAnexoRequeridoPessoaFormGroup();

  compareAnexoPessoa = (o1: IAnexoPessoa | null, o2: IAnexoPessoa | null): boolean => this.anexoPessoaService.compareAnexoPessoa(o1, o2);

  compareAnexoRequerido = (o1: IAnexoRequerido | null, o2: IAnexoRequerido | null): boolean =>
    this.anexoRequeridoService.compareAnexoRequerido(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoRequeridoPessoa }) => {
      this.anexoRequeridoPessoa = anexoRequeridoPessoa;
      if (anexoRequeridoPessoa) {
        this.updateForm(anexoRequeridoPessoa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexoRequeridoPessoa = this.anexoRequeridoPessoaFormService.getAnexoRequeridoPessoa(this.editForm);
    if (anexoRequeridoPessoa.id !== null) {
      this.subscribeToSaveResponse(this.anexoRequeridoPessoaService.update(anexoRequeridoPessoa));
    } else {
      this.subscribeToSaveResponse(this.anexoRequeridoPessoaService.create(anexoRequeridoPessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoRequeridoPessoa>>): void {
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

  protected updateForm(anexoRequeridoPessoa: IAnexoRequeridoPessoa): void {
    this.anexoRequeridoPessoa = anexoRequeridoPessoa;
    this.anexoRequeridoPessoaFormService.resetForm(this.editForm, anexoRequeridoPessoa);

    this.anexoPessoasSharedCollection = this.anexoPessoaService.addAnexoPessoaToCollectionIfMissing<IAnexoPessoa>(
      this.anexoPessoasSharedCollection,
      anexoRequeridoPessoa.anexoPessoa,
    );
    this.anexoRequeridosSharedCollection = this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
      this.anexoRequeridosSharedCollection,
      anexoRequeridoPessoa.anexoRequerido,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.anexoPessoaService
      .query()
      .pipe(map((res: HttpResponse<IAnexoPessoa[]>) => res.body ?? []))
      .pipe(
        map((anexoPessoas: IAnexoPessoa[]) =>
          this.anexoPessoaService.addAnexoPessoaToCollectionIfMissing<IAnexoPessoa>(anexoPessoas, this.anexoRequeridoPessoa?.anexoPessoa),
        ),
      )
      .subscribe((anexoPessoas: IAnexoPessoa[]) => (this.anexoPessoasSharedCollection = anexoPessoas));

    this.anexoRequeridoService
      .query()
      .pipe(map((res: HttpResponse<IAnexoRequerido[]>) => res.body ?? []))
      .pipe(
        map((anexoRequeridos: IAnexoRequerido[]) =>
          this.anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing<IAnexoRequerido>(
            anexoRequeridos,
            this.anexoRequeridoPessoa?.anexoRequerido,
          ),
        ),
      )
      .subscribe((anexoRequeridos: IAnexoRequerido[]) => (this.anexoRequeridosSharedCollection = anexoRequeridos));
  }
}
