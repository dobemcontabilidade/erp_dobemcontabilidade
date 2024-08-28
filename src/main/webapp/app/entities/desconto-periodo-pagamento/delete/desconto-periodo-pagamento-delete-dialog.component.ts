import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';
import { DescontoPeriodoPagamentoService } from '../service/desconto-periodo-pagamento.service';

@Component({
  standalone: true,
  templateUrl: './desconto-periodo-pagamento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DescontoPeriodoPagamentoDeleteDialogComponent {
  descontoPeriodoPagamento?: IDescontoPeriodoPagamento;

  protected descontoPeriodoPagamentoService = inject(DescontoPeriodoPagamentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.descontoPeriodoPagamentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
