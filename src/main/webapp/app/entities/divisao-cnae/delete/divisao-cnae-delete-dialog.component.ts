import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDivisaoCnae } from '../divisao-cnae.model';
import { DivisaoCnaeService } from '../service/divisao-cnae.service';

@Component({
  standalone: true,
  templateUrl: './divisao-cnae-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DivisaoCnaeDeleteDialogComponent {
  divisaoCnae?: IDivisaoCnae;

  protected divisaoCnaeService = inject(DivisaoCnaeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.divisaoCnaeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
