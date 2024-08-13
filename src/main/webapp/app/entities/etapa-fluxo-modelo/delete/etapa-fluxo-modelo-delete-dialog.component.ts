import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';
import { EtapaFluxoModeloService } from '../service/etapa-fluxo-modelo.service';

@Component({
  standalone: true,
  templateUrl: './etapa-fluxo-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EtapaFluxoModeloDeleteDialogComponent {
  etapaFluxoModelo?: IEtapaFluxoModelo;

  protected etapaFluxoModeloService = inject(EtapaFluxoModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etapaFluxoModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
