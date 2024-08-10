import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGrupoCnae } from '../grupo-cnae.model';
import { GrupoCnaeService } from '../service/grupo-cnae.service';

@Component({
  standalone: true,
  templateUrl: './grupo-cnae-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GrupoCnaeDeleteDialogComponent {
  grupoCnae?: IGrupoCnae;

  protected grupoCnaeService = inject(GrupoCnaeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoCnaeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
