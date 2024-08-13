import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPeriodoPagamento } from '../periodo-pagamento.model';
import { PeriodoPagamentoService } from '../service/periodo-pagamento.service';

@Component({
  standalone: true,
  templateUrl: './periodo-pagamento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PeriodoPagamentoDeleteDialogComponent {
  periodoPagamento?: IPeriodoPagamento;

  protected periodoPagamentoService = inject(PeriodoPagamentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodoPagamentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
