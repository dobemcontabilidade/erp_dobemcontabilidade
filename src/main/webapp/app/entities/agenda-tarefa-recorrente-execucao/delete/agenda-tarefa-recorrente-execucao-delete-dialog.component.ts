import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';
import { AgendaTarefaRecorrenteExecucaoService } from '../service/agenda-tarefa-recorrente-execucao.service';

@Component({
  standalone: true,
  templateUrl: './agenda-tarefa-recorrente-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AgendaTarefaRecorrenteExecucaoDeleteDialogComponent {
  agendaTarefaRecorrenteExecucao?: IAgendaTarefaRecorrenteExecucao;

  protected agendaTarefaRecorrenteExecucaoService = inject(AgendaTarefaRecorrenteExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agendaTarefaRecorrenteExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
