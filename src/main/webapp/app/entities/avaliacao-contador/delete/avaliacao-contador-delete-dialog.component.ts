import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAvaliacaoContador } from '../avaliacao-contador.model';
import { AvaliacaoContadorService } from '../service/avaliacao-contador.service';

@Component({
  standalone: true,
  templateUrl: './avaliacao-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AvaliacaoContadorDeleteDialogComponent {
  avaliacaoContador?: IAvaliacaoContador;

  protected avaliacaoContadorService = inject(AvaliacaoContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avaliacaoContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
