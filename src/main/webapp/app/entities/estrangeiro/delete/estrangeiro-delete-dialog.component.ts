import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEstrangeiro } from '../estrangeiro.model';
import { EstrangeiroService } from '../service/estrangeiro.service';

@Component({
  standalone: true,
  templateUrl: './estrangeiro-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EstrangeiroDeleteDialogComponent {
  estrangeiro?: IEstrangeiro;

  protected estrangeiroService = inject(EstrangeiroService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estrangeiroService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
