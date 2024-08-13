import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITipoDenuncia } from '../tipo-denuncia.model';
import { TipoDenunciaService } from '../service/tipo-denuncia.service';

@Component({
  standalone: true,
  templateUrl: './tipo-denuncia-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TipoDenunciaDeleteDialogComponent {
  tipoDenuncia?: ITipoDenuncia;

  protected tipoDenunciaService = inject(TipoDenunciaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoDenunciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
