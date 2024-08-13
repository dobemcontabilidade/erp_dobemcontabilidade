import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IImpostoParcelado } from '../imposto-parcelado.model';
import { ImpostoParceladoService } from '../service/imposto-parcelado.service';

@Component({
  standalone: true,
  templateUrl: './imposto-parcelado-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ImpostoParceladoDeleteDialogComponent {
  impostoParcelado?: IImpostoParcelado;

  protected impostoParceladoService = inject(ImpostoParceladoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.impostoParceladoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
