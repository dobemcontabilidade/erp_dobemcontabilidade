import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';
import { TarefaEmpresaModeloService } from '../service/tarefa-empresa-modelo.service';

@Component({
  standalone: true,
  templateUrl: './tarefa-empresa-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TarefaEmpresaModeloDeleteDialogComponent {
  tarefaEmpresaModelo?: ITarefaEmpresaModelo;

  protected tarefaEmpresaModeloService = inject(TarefaEmpresaModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarefaEmpresaModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
