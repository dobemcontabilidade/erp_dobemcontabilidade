import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
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

  empresasSharedCollection: IEmpresa[] = [];
  cidadesSharedCollection: ICidade[] = [];

  protected enderecoEmpresaService = inject(EnderecoEmpresaService);
  protected enderecoEmpresaFormService = inject(EnderecoEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected cidadeService = inject(CidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EnderecoEmpresaFormGroup = this.enderecoEmpresaFormService.createEnderecoEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

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

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      enderecoEmpresa.empresa,
    );
    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      enderecoEmpresa.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.enderecoEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.enderecoEmpresa?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
