import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';
import { AnexoOrdemServicoExecucaoService } from '../service/anexo-ordem-servico-execucao.service';

@Component({
  standalone: true,
  templateUrl: './anexo-ordem-servico-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoOrdemServicoExecucaoDeleteDialogComponent {
  anexoOrdemServicoExecucao?: IAnexoOrdemServicoExecucao;

  protected anexoOrdemServicoExecucaoService = inject(AnexoOrdemServicoExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoOrdemServicoExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
