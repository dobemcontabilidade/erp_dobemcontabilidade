import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFormaDePagamento } from '../forma-de-pagamento.model';
import { FormaDePagamentoService } from '../service/forma-de-pagamento.service';
import { FormaDePagamentoFormService, FormaDePagamentoFormGroup } from './forma-de-pagamento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-forma-de-pagamento-update',
  templateUrl: './forma-de-pagamento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FormaDePagamentoUpdateComponent implements OnInit {
  isSaving = false;
  formaDePagamento: IFormaDePagamento | null = null;

  protected formaDePagamentoService = inject(FormaDePagamentoService);
  protected formaDePagamentoFormService = inject(FormaDePagamentoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormaDePagamentoFormGroup = this.formaDePagamentoFormService.createFormaDePagamentoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formaDePagamento }) => {
      this.formaDePagamento = formaDePagamento;
      if (formaDePagamento) {
        this.updateForm(formaDePagamento);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formaDePagamento = this.formaDePagamentoFormService.getFormaDePagamento(this.editForm);
    if (formaDePagamento.id !== null) {
      this.subscribeToSaveResponse(this.formaDePagamentoService.update(formaDePagamento));
    } else {
      this.subscribeToSaveResponse(this.formaDePagamentoService.create(formaDePagamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormaDePagamento>>): void {
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

  protected updateForm(formaDePagamento: IFormaDePagamento): void {
    this.formaDePagamento = formaDePagamento;
    this.formaDePagamentoFormService.resetForm(this.editForm, formaDePagamento);
  }
}
