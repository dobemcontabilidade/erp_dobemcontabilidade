import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';
import { EtapaFluxoExecucaoService } from '../service/etapa-fluxo-execucao.service';

@Component({
  standalone: true,
  templateUrl: './etapa-fluxo-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EtapaFluxoExecucaoDeleteDialogComponent {
  etapaFluxoExecucao?: IEtapaFluxoExecucao;

  protected etapaFluxoExecucaoService = inject(EtapaFluxoExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etapaFluxoExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
