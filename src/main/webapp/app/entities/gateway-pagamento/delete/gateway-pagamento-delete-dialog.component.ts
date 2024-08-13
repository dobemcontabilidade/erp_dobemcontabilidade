import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGatewayPagamento } from '../gateway-pagamento.model';
import { GatewayPagamentoService } from '../service/gateway-pagamento.service';

@Component({
  standalone: true,
  templateUrl: './gateway-pagamento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GatewayPagamentoDeleteDialogComponent {
  gatewayPagamento?: IGatewayPagamento;

  protected gatewayPagamentoService = inject(GatewayPagamentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gatewayPagamentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
