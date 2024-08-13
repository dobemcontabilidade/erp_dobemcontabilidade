import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';
import { SubTarefaOrdemServicoService } from '../service/sub-tarefa-ordem-servico.service';

@Component({
  standalone: true,
  templateUrl: './sub-tarefa-ordem-servico-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SubTarefaOrdemServicoDeleteDialogComponent {
  subTarefaOrdemServico?: ISubTarefaOrdemServico;

  protected subTarefaOrdemServicoService = inject(SubTarefaOrdemServicoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subTarefaOrdemServicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
