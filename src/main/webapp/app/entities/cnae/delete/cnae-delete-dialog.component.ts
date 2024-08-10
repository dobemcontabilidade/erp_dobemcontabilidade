import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICnae } from '../cnae.model';
import { CnaeService } from '../service/cnae.service';

@Component({
  standalone: true,
  templateUrl: './cnae-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CnaeDeleteDialogComponent {
  cnae?: ICnae;

  protected cnaeService = inject(CnaeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cnaeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
