import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { EnderecoPessoaService } from '../service/endereco-pessoa.service';
import { IEnderecoPessoa } from '../endereco-pessoa.model';
import { EnderecoPessoaFormService, EnderecoPessoaFormGroup } from './endereco-pessoa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-endereco-pessoa-update',
  templateUrl: './endereco-pessoa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EnderecoPessoaUpdateComponent implements OnInit {
  isSaving = false;
  enderecoPessoa: IEnderecoPessoa | null = null;

  pessoasSharedCollection: IPessoa[] = [];
  cidadesSharedCollection: ICidade[] = [];

  protected enderecoPessoaService = inject(EnderecoPessoaService);
  protected enderecoPessoaFormService = inject(EnderecoPessoaFormService);
  protected pessoaService = inject(PessoaService);
  protected cidadeService = inject(CidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EnderecoPessoaFormGroup = this.enderecoPessoaFormService.createEnderecoPessoaFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enderecoPessoa }) => {
      this.enderecoPessoa = enderecoPessoa;
      if (enderecoPessoa) {
        this.updateForm(enderecoPessoa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enderecoPessoa = this.enderecoPessoaFormService.getEnderecoPessoa(this.editForm);
    if (enderecoPessoa.id !== null) {
      this.subscribeToSaveResponse(this.enderecoPessoaService.update(enderecoPessoa));
    } else {
      this.subscribeToSaveResponse(this.enderecoPessoaService.create(enderecoPessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnderecoPessoa>>): void {
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

  protected updateForm(enderecoPessoa: IEnderecoPessoa): void {
    this.enderecoPessoa = enderecoPessoa;
    this.enderecoPessoaFormService.resetForm(this.editForm, enderecoPessoa);

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(
      this.pessoasSharedCollection,
      enderecoPessoa.pessoa,
    );
    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      enderecoPessoa.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.enderecoPessoa?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));

    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.enderecoPessoa?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
