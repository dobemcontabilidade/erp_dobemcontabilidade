import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';
import { AnexoTarefaRecorrenteExecucaoService } from '../service/anexo-tarefa-recorrente-execucao.service';

@Component({
  standalone: true,
  templateUrl: './anexo-tarefa-recorrente-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoTarefaRecorrenteExecucaoDeleteDialogComponent {
  anexoTarefaRecorrenteExecucao?: IAnexoTarefaRecorrenteExecucao;

  protected anexoTarefaRecorrenteExecucaoService = inject(AnexoTarefaRecorrenteExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoTarefaRecorrenteExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
