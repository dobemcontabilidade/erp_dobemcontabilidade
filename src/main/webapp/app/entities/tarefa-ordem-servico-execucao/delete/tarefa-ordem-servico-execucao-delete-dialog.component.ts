import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from '../service/tarefa-ordem-servico-execucao.service';

@Component({
  standalone: true,
  templateUrl: './tarefa-ordem-servico-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TarefaOrdemServicoExecucaoDeleteDialogComponent {
  tarefaOrdemServicoExecucao?: ITarefaOrdemServicoExecucao;

  protected tarefaOrdemServicoExecucaoService = inject(TarefaOrdemServicoExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarefaOrdemServicoExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
