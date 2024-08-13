import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { SituacaoPagamentoEnum } from 'app/entities/enumerations/situacao-pagamento-enum.model';
import { PagamentoService } from '../service/pagamento.service';
import { IPagamento } from '../pagamento.model';
import { PagamentoFormService, PagamentoFormGroup } from './pagamento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-pagamento-update',
  templateUrl: './pagamento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PagamentoUpdateComponent implements OnInit {
  isSaving = false;
  pagamento: IPagamento | null = null;
  situacaoPagamentoEnumValues = Object.keys(SituacaoPagamentoEnum);

  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];

  protected pagamentoService = inject(PagamentoService);
  protected pagamentoFormService = inject(PagamentoFormService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PagamentoFormGroup = this.pagamentoFormService.createPagamentoFormGroup();

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagamento }) => {
      this.pagamento = pagamento;
      if (pagamento) {
        this.updateForm(pagamento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pagamento = this.pagamentoFormService.getPagamento(this.editForm);
    if (pagamento.id !== null) {
      this.subscribeToSaveResponse(this.pagamentoService.update(pagamento));
    } else {
      this.subscribeToSaveResponse(this.pagamentoService.create(pagamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagamento>>): void {
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

  protected updateForm(pagamento: IPagamento): void {
    this.pagamento = pagamento;
    this.pagamentoFormService.resetForm(this.editForm, pagamento);

    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      pagamento.assinaturaEmpresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.assinaturaEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IAssinaturaEmpresa[]>) => res.body ?? []))
      .pipe(
        map((assinaturaEmpresas: IAssinaturaEmpresa[]) =>
          this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
            assinaturaEmpresas,
            this.pagamento?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));
  }
}
