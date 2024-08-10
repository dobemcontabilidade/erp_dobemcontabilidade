import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPrazoAssinatura } from '../prazo-assinatura.model';
import { PrazoAssinaturaService } from '../service/prazo-assinatura.service';

@Component({
  standalone: true,
  templateUrl: './prazo-assinatura-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PrazoAssinaturaDeleteDialogComponent {
  prazoAssinatura?: IPrazoAssinatura;

  protected prazoAssinaturaService = inject(PrazoAssinaturaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prazoAssinaturaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
