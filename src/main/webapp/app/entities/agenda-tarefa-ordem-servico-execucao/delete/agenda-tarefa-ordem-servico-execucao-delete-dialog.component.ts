import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';
import { AgendaTarefaOrdemServicoExecucaoService } from '../service/agenda-tarefa-ordem-servico-execucao.service';

@Component({
  standalone: true,
  templateUrl: './agenda-tarefa-ordem-servico-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent {
  agendaTarefaOrdemServicoExecucao?: IAgendaTarefaOrdemServicoExecucao;

  protected agendaTarefaOrdemServicoExecucaoService = inject(AgendaTarefaOrdemServicoExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agendaTarefaOrdemServicoExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
