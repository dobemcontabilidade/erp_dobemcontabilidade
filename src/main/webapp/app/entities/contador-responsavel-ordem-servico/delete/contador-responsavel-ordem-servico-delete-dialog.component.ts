import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';
import { ContadorResponsavelOrdemServicoService } from '../service/contador-responsavel-ordem-servico.service';

@Component({
  standalone: true,
  templateUrl: './contador-responsavel-ordem-servico-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContadorResponsavelOrdemServicoDeleteDialogComponent {
  contadorResponsavelOrdemServico?: IContadorResponsavelOrdemServico;

  protected contadorResponsavelOrdemServicoService = inject(ContadorResponsavelOrdemServicoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contadorResponsavelOrdemServicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
