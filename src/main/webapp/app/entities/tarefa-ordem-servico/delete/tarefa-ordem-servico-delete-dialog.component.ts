import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITarefaOrdemServico } from '../tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from '../service/tarefa-ordem-servico.service';

@Component({
  standalone: true,
  templateUrl: './tarefa-ordem-servico-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TarefaOrdemServicoDeleteDialogComponent {
  tarefaOrdemServico?: ITarefaOrdemServico;

  protected tarefaOrdemServicoService = inject(TarefaOrdemServicoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarefaOrdemServicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
