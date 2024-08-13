import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';
import { TarefaRecorrenteEmpresaModeloService } from '../service/tarefa-recorrente-empresa-modelo.service';

@Component({
  standalone: true,
  templateUrl: './tarefa-recorrente-empresa-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TarefaRecorrenteEmpresaModeloDeleteDialogComponent {
  tarefaRecorrenteEmpresaModelo?: ITarefaRecorrenteEmpresaModelo;

  protected tarefaRecorrenteEmpresaModeloService = inject(TarefaRecorrenteEmpresaModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarefaRecorrenteEmpresaModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
