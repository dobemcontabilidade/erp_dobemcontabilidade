import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFormaDePagamento } from '../forma-de-pagamento.model';
import { FormaDePagamentoService } from '../service/forma-de-pagamento.service';

@Component({
  standalone: true,
  templateUrl: './forma-de-pagamento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FormaDePagamentoDeleteDialogComponent {
  formaDePagamento?: IFormaDePagamento;

  protected formaDePagamentoService = inject(FormaDePagamentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formaDePagamentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
