import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';
import { ServicoContabilOrdemServicoService } from '../service/servico-contabil-ordem-servico.service';

@Component({
  standalone: true,
  templateUrl: './servico-contabil-ordem-servico-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServicoContabilOrdemServicoDeleteDialogComponent {
  servicoContabilOrdemServico?: IServicoContabilOrdemServico;

  protected servicoContabilOrdemServicoService = inject(ServicoContabilOrdemServicoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicoContabilOrdemServicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
