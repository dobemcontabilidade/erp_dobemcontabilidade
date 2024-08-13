import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContadorResponsavelTarefaRecorrente } from '../contador-responsavel-tarefa-recorrente.model';
import { ContadorResponsavelTarefaRecorrenteService } from '../service/contador-responsavel-tarefa-recorrente.service';

@Component({
  standalone: true,
  templateUrl: './contador-responsavel-tarefa-recorrente-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContadorResponsavelTarefaRecorrenteDeleteDialogComponent {
  contadorResponsavelTarefaRecorrente?: IContadorResponsavelTarefaRecorrente;

  protected contadorResponsavelTarefaRecorrenteService = inject(ContadorResponsavelTarefaRecorrenteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contadorResponsavelTarefaRecorrenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
