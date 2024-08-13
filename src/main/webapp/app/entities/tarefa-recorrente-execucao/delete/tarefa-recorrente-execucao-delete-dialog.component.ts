import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from '../service/tarefa-recorrente-execucao.service';

@Component({
  standalone: true,
  templateUrl: './tarefa-recorrente-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TarefaRecorrenteExecucaoDeleteDialogComponent {
  tarefaRecorrenteExecucao?: ITarefaRecorrenteExecucao;

  protected tarefaRecorrenteExecucaoService = inject(TarefaRecorrenteExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarefaRecorrenteExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
