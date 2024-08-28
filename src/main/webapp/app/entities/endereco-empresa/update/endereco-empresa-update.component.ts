import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { EnderecoEmpresaService } from '../service/endereco-empresa.service';
import { IEnderecoEmpresa } from '../endereco-empresa.model';
import { EnderecoEmpresaFormService, EnderecoEmpresaFormGroup } from './endereco-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-endereco-empresa-update',
  templateUrl: './endereco-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EnderecoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  enderecoEmpresa: IEnderecoEmpresa | null = null;

  cidadesSharedCollection: ICidade[] = [];
  pessoajuridicasSharedCollection: IPessoajuridica[] = [];

  protected enderecoEmpresaService = inject(EnderecoEmpresaService);
  protected enderecoEmpresaFormService = inject(EnderecoEmpresaFormService);
  protected cidadeService = inject(CidadeService);
  protected pessoajuridicaService = inject(PessoajuridicaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EnderecoEmpresaFormGroup = this.enderecoEmpresaFormService.createEnderecoEmpresaFormGroup();

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  comparePessoajuridica = (o1: IPessoajuridica | null, o2: IPessoajuridica | null): boolean =>
    this.pessoajuridicaService.comparePessoajuridica(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enderecoEmpresa }) => {
      this.enderecoEmpresa = enderecoEmpresa;
      if (enderecoEmpresa) {
        this.updateForm(enderecoEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enderecoEmpresa = this.enderecoEmpresaFormService.getEnderecoEmpresa(this.editForm);
    if (enderecoEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.enderecoEmpresaService.update(enderecoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.enderecoEmpresaService.create(enderecoEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnderecoEmpresa>>): void {
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

  protected updateForm(enderecoEmpresa: IEnderecoEmpresa): void {
    this.enderecoEmpresa = enderecoEmpresa;
    this.enderecoEmpresaFormService.resetForm(this.editForm, enderecoEmpresa);

    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      enderecoEmpresa.cidade,
    );
    this.pessoajuridicasSharedCollection = this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
      this.pessoajuridicasSharedCollection,
      enderecoEmpresa.pessoaJuridica,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.enderecoEmpresa?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));

    this.pessoajuridicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoajuridica[]>) => res.body ?? []))
      .pipe(
        map((pessoajuridicas: IPessoajuridica[]) =>
          this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
            pessoajuridicas,
            this.enderecoEmpresa?.pessoaJuridica,
          ),
        ),
      )
      .subscribe((pessoajuridicas: IPessoajuridica[]) => (this.pessoajuridicasSharedCollection = pessoajuridicas));
  }
}
