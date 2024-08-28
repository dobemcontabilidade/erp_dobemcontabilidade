import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
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

  pessoaFisicasSharedCollection: IPessoaFisica[] = [];
  cidadesSharedCollection: ICidade[] = [];

  protected enderecoPessoaService = inject(EnderecoPessoaService);
  protected enderecoPessoaFormService = inject(EnderecoPessoaFormService);
  protected pessoaFisicaService = inject(PessoaFisicaService);
  protected cidadeService = inject(CidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EnderecoPessoaFormGroup = this.enderecoPessoaFormService.createEnderecoPessoaFormGroup();

  comparePessoaFisica = (o1: IPessoaFisica | null, o2: IPessoaFisica | null): boolean =>
    this.pessoaFisicaService.comparePessoaFisica(o1, o2);

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

    this.pessoaFisicasSharedCollection = this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(
      this.pessoaFisicasSharedCollection,
      enderecoPessoa.pessoa,
    );
    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      enderecoPessoa.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaFisicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoaFisica[]>) => res.body ?? []))
      .pipe(
        map((pessoaFisicas: IPessoaFisica[]) =>
          this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(pessoaFisicas, this.enderecoPessoa?.pessoa),
        ),
      )
      .subscribe((pessoaFisicas: IPessoaFisica[]) => (this.pessoaFisicasSharedCollection = pessoaFisicas));

    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.enderecoPessoa?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
