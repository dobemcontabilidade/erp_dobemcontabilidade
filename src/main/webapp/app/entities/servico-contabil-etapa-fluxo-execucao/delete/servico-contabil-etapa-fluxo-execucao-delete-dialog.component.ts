import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';
import { ServicoContabilEtapaFluxoExecucaoService } from '../service/servico-contabil-etapa-fluxo-execucao.service';

@Component({
  standalone: true,
  templateUrl: './servico-contabil-etapa-fluxo-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServicoContabilEtapaFluxoExecucaoDeleteDialogComponent {
  servicoContabilEtapaFluxoExecucao?: IServicoContabilEtapaFluxoExecucao;

  protected servicoContabilEtapaFluxoExecucaoService = inject(ServicoContabilEtapaFluxoExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicoContabilEtapaFluxoExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
