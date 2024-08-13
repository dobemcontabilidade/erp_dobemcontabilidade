import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAtorAvaliado } from '../ator-avaliado.model';
import { AtorAvaliadoService } from '../service/ator-avaliado.service';

@Component({
  standalone: true,
  templateUrl: './ator-avaliado-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AtorAvaliadoDeleteDialogComponent {
  atorAvaliado?: IAtorAvaliado;

  protected atorAvaliadoService = inject(AtorAvaliadoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.atorAvaliadoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
