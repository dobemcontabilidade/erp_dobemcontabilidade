import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITarefaRecorrente } from '../tarefa-recorrente.model';
import { TarefaRecorrenteService } from '../service/tarefa-recorrente.service';

@Component({
  standalone: true,
  templateUrl: './tarefa-recorrente-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TarefaRecorrenteDeleteDialogComponent {
  tarefaRecorrente?: ITarefaRecorrente;

  protected tarefaRecorrenteService = inject(TarefaRecorrenteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarefaRecorrenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
