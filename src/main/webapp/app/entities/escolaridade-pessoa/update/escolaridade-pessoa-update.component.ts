import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEscolaridade } from 'app/entities/escolaridade/escolaridade.model';
import { EscolaridadeService } from 'app/entities/escolaridade/service/escolaridade.service';
import { EscolaridadePessoaService } from '../service/escolaridade-pessoa.service';
import { IEscolaridadePessoa } from '../escolaridade-pessoa.model';
import { EscolaridadePessoaFormService, EscolaridadePessoaFormGroup } from './escolaridade-pessoa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-escolaridade-pessoa-update',
  templateUrl: './escolaridade-pessoa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EscolaridadePessoaUpdateComponent implements OnInit {
  isSaving = false;
  escolaridadePessoa: IEscolaridadePessoa | null = null;

  pessoasSharedCollection: IPessoa[] = [];
  escolaridadesSharedCollection: IEscolaridade[] = [];

  protected escolaridadePessoaService = inject(EscolaridadePessoaService);
  protected escolaridadePessoaFormService = inject(EscolaridadePessoaFormService);
  protected pessoaService = inject(PessoaService);
  protected escolaridadeService = inject(EscolaridadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EscolaridadePessoaFormGroup = this.escolaridadePessoaFormService.createEscolaridadePessoaFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareEscolaridade = (o1: IEscolaridade | null, o2: IEscolaridade | null): boolean =>
    this.escolaridadeService.compareEscolaridade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ escolaridadePessoa }) => {
      this.escolaridadePessoa = escolaridadePessoa;
      if (escolaridadePessoa) {
        this.updateForm(escolaridadePessoa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const escolaridadePessoa = this.escolaridadePessoaFormService.getEscolaridadePessoa(this.editForm);
    if (escolaridadePessoa.id !== null) {
      this.subscribeToSaveResponse(this.escolaridadePessoaService.update(escolaridadePessoa));
    } else {
      this.subscribeToSaveResponse(this.escolaridadePessoaService.create(escolaridadePessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEscolaridadePessoa>>): void {
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

  protected updateForm(escolaridadePessoa: IEscolaridadePessoa): void {
    this.escolaridadePessoa = escolaridadePessoa;
    this.escolaridadePessoaFormService.resetForm(this.editForm, escolaridadePessoa);

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(
      this.pessoasSharedCollection,
      escolaridadePessoa.pessoa,
    );
    this.escolaridadesSharedCollection = this.escolaridadeService.addEscolaridadeToCollectionIfMissing<IEscolaridade>(
      this.escolaridadesSharedCollection,
      escolaridadePessoa.escolaridade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(
        map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.escolaridadePessoa?.pessoa)),
      )
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));

    this.escolaridadeService
      .query()
      .pipe(map((res: HttpResponse<IEscolaridade[]>) => res.body ?? []))
      .pipe(
        map((escolaridades: IEscolaridade[]) =>
          this.escolaridadeService.addEscolaridadeToCollectionIfMissing<IEscolaridade>(
            escolaridades,
            this.escolaridadePessoa?.escolaridade,
          ),
        ),
      )
      .subscribe((escolaridades: IEscolaridade[]) => (this.escolaridadesSharedCollection = escolaridades));
  }
}
