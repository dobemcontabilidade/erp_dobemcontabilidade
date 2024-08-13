import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IFormaDePagamento } from 'app/entities/forma-de-pagamento/forma-de-pagamento.model';
import { FormaDePagamentoService } from 'app/entities/forma-de-pagamento/service/forma-de-pagamento.service';
import { SituacaoCobrancaEnum } from 'app/entities/enumerations/situacao-cobranca-enum.model';
import { CobrancaEmpresaService } from '../service/cobranca-empresa.service';
import { ICobrancaEmpresa } from '../cobranca-empresa.model';
import { CobrancaEmpresaFormService, CobrancaEmpresaFormGroup } from './cobranca-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cobranca-empresa-update',
  templateUrl: './cobranca-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CobrancaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  cobrancaEmpresa: ICobrancaEmpresa | null = null;
  situacaoCobrancaEnumValues = Object.keys(SituacaoCobrancaEnum);

  assinaturaEmpresasSharedCollection: IAssinaturaEmpresa[] = [];
  formaDePagamentosSharedCollection: IFormaDePagamento[] = [];

  protected cobrancaEmpresaService = inject(CobrancaEmpresaService);
  protected cobrancaEmpresaFormService = inject(CobrancaEmpresaFormService);
  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected formaDePagamentoService = inject(FormaDePagamentoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CobrancaEmpresaFormGroup = this.cobrancaEmpresaFormService.createCobrancaEmpresaFormGroup();

  compareAssinaturaEmpresa = (o1: IAssinaturaEmpresa | null, o2: IAssinaturaEmpresa | null): boolean =>
    this.assinaturaEmpresaService.compareAssinaturaEmpresa(o1, o2);

  compareFormaDePagamento = (o1: IFormaDePagamento | null, o2: IFormaDePagamento | null): boolean =>
    this.formaDePagamentoService.compareFormaDePagamento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cobrancaEmpresa }) => {
      this.cobrancaEmpresa = cobrancaEmpresa;
      if (cobrancaEmpresa) {
        this.updateForm(cobrancaEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cobrancaEmpresa = this.cobrancaEmpresaFormService.getCobrancaEmpresa(this.editForm);
    if (cobrancaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.cobrancaEmpresaService.update(cobrancaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.cobrancaEmpresaService.create(cobrancaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICobrancaEmpresa>>): void {
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

  protected updateForm(cobrancaEmpresa: ICobrancaEmpresa): void {
    this.cobrancaEmpresa = cobrancaEmpresa;
    this.cobrancaEmpresaFormService.resetForm(this.editForm, cobrancaEmpresa);

    this.assinaturaEmpresasSharedCollection = this.assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing<IAssinaturaEmpresa>(
      this.assinaturaEmpresasSharedCollection,
      cobrancaEmpresa.assinaturaEmpresa,
    );
    this.formaDePagamentosSharedCollection = this.formaDePagamentoService.addFormaDePagamentoToCollectionIfMissing<IFormaDePagamento>(
      this.formaDePagamentosSharedCollection,
      cobrancaEmpresa.formaDePagamento,
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
            this.cobrancaEmpresa?.assinaturaEmpresa,
          ),
        ),
      )
      .subscribe((assinaturaEmpresas: IAssinaturaEmpresa[]) => (this.assinaturaEmpresasSharedCollection = assinaturaEmpresas));

    this.formaDePagamentoService
      .query()
      .pipe(map((res: HttpResponse<IFormaDePagamento[]>) => res.body ?? []))
      .pipe(
        map((formaDePagamentos: IFormaDePagamento[]) =>
          this.formaDePagamentoService.addFormaDePagamentoToCollectionIfMissing<IFormaDePagamento>(
            formaDePagamentos,
            this.cobrancaEmpresa?.formaDePagamento,
          ),
        ),
      )
      .subscribe((formaDePagamentos: IFormaDePagamento[]) => (this.formaDePagamentosSharedCollection = formaDePagamentos));
  }
}
