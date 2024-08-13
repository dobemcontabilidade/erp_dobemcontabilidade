import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';
import { GatewayAssinaturaEmpresaService } from '../service/gateway-assinatura-empresa.service';

@Component({
  standalone: true,
  templateUrl: './gateway-assinatura-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GatewayAssinaturaEmpresaDeleteDialogComponent {
  gatewayAssinaturaEmpresa?: IGatewayAssinaturaEmpresa;

  protected gatewayAssinaturaEmpresaService = inject(GatewayAssinaturaEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gatewayAssinaturaEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
