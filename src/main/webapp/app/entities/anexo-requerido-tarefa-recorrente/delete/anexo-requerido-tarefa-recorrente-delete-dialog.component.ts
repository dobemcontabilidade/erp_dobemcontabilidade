import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';
import { AnexoRequeridoTarefaRecorrenteService } from '../service/anexo-requerido-tarefa-recorrente.service';

@Component({
  standalone: true,
  templateUrl: './anexo-requerido-tarefa-recorrente-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoRequeridoTarefaRecorrenteDeleteDialogComponent {
  anexoRequeridoTarefaRecorrente?: IAnexoRequeridoTarefaRecorrente;

  protected anexoRequeridoTarefaRecorrenteService = inject(AnexoRequeridoTarefaRecorrenteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoRequeridoTarefaRecorrenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
