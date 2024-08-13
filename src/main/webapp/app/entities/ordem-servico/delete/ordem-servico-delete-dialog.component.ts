import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrdemServico } from '../ordem-servico.model';
import { OrdemServicoService } from '../service/ordem-servico.service';

@Component({
  standalone: true,
  templateUrl: './ordem-servico-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrdemServicoDeleteDialogComponent {
  ordemServico?: IOrdemServico;

  protected ordemServicoService = inject(OrdemServicoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ordemServicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
