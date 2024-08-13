import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';
import { AnexoRequeridoTarefaOrdemServicoService } from '../service/anexo-requerido-tarefa-ordem-servico.service';

@Component({
  standalone: true,
  templateUrl: './anexo-requerido-tarefa-ordem-servico-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoRequeridoTarefaOrdemServicoDeleteDialogComponent {
  anexoRequeridoTarefaOrdemServico?: IAnexoRequeridoTarefaOrdemServico;

  protected anexoRequeridoTarefaOrdemServicoService = inject(AnexoRequeridoTarefaOrdemServicoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoRequeridoTarefaOrdemServicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
