import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISubTarefaRecorrente } from '../sub-tarefa-recorrente.model';
import { SubTarefaRecorrenteService } from '../service/sub-tarefa-recorrente.service';

@Component({
  standalone: true,
  templateUrl: './sub-tarefa-recorrente-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SubTarefaRecorrenteDeleteDialogComponent {
  subTarefaRecorrente?: ISubTarefaRecorrente;

  protected subTarefaRecorrenteService = inject(SubTarefaRecorrenteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subTarefaRecorrenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
