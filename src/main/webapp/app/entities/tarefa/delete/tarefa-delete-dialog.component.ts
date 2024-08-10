import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITarefa } from '../tarefa.model';
import { TarefaService } from '../service/tarefa.service';

@Component({
  standalone: true,
  templateUrl: './tarefa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TarefaDeleteDialogComponent {
  tarefa?: ITarefa;

  protected tarefaService = inject(TarefaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarefaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
