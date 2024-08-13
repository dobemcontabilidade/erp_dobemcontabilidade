import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';
import { ServicoContabilEtapaFluxoModeloService } from '../service/servico-contabil-etapa-fluxo-modelo.service';

@Component({
  standalone: true,
  templateUrl: './servico-contabil-etapa-fluxo-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServicoContabilEtapaFluxoModeloDeleteDialogComponent {
  servicoContabilEtapaFluxoModelo?: IServicoContabilEtapaFluxoModelo;

  protected servicoContabilEtapaFluxoModeloService = inject(ServicoContabilEtapaFluxoModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicoContabilEtapaFluxoModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
