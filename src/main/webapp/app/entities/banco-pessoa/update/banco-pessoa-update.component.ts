import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IBanco } from 'app/entities/banco/banco.model';
import { BancoService } from 'app/entities/banco/service/banco.service';
import { TipoContaBancoEnum } from 'app/entities/enumerations/tipo-conta-banco-enum.model';
import { BancoPessoaService } from '../service/banco-pessoa.service';
import { IBancoPessoa } from '../banco-pessoa.model';
import { BancoPessoaFormService, BancoPessoaFormGroup } from './banco-pessoa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-banco-pessoa-update',
  templateUrl: './banco-pessoa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BancoPessoaUpdateComponent implements OnInit {
  isSaving = false;
  bancoPessoa: IBancoPessoa | null = null;
  tipoContaBancoEnumValues = Object.keys(TipoContaBancoEnum);

  pessoasSharedCollection: IPessoa[] = [];
  bancosSharedCollection: IBanco[] = [];

  protected bancoPessoaService = inject(BancoPessoaService);
  protected bancoPessoaFormService = inject(BancoPessoaFormService);
  protected pessoaService = inject(PessoaService);
  protected bancoService = inject(BancoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BancoPessoaFormGroup = this.bancoPessoaFormService.createBancoPessoaFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareBanco = (o1: IBanco | null, o2: IBanco | null): boolean => this.bancoService.compareBanco(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bancoPessoa }) => {
      this.bancoPessoa = bancoPessoa;
      if (bancoPessoa) {
        this.updateForm(bancoPessoa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bancoPessoa = this.bancoPessoaFormService.getBancoPessoa(this.editForm);
    if (bancoPessoa.id !== null) {
      this.subscribeToSaveResponse(this.bancoPessoaService.update(bancoPessoa));
    } else {
      this.subscribeToSaveResponse(this.bancoPessoaService.create(bancoPessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBancoPessoa>>): void {
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

  protected updateForm(bancoPessoa: IBancoPessoa): void {
    this.bancoPessoa = bancoPessoa;
    this.bancoPessoaFormService.resetForm(this.editForm, bancoPessoa);

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(
      this.pessoasSharedCollection,
      bancoPessoa.pessoa,
    );
    this.bancosSharedCollection = this.bancoService.addBancoToCollectionIfMissing<IBanco>(this.bancosSharedCollection, bancoPessoa.banco);
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.bancoPessoa?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));

    this.bancoService
      .query()
      .pipe(map((res: HttpResponse<IBanco[]>) => res.body ?? []))
      .pipe(map((bancos: IBanco[]) => this.bancoService.addBancoToCollectionIfMissing<IBanco>(bancos, this.bancoPessoa?.banco)))
      .subscribe((bancos: IBanco[]) => (this.bancosSharedCollection = bancos));
  }
}
