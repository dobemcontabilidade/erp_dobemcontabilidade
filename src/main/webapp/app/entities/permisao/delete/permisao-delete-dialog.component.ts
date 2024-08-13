import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPermisao } from '../permisao.model';
import { PermisaoService } from '../service/permisao.service';

@Component({
  standalone: true,
  templateUrl: './permisao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PermisaoDeleteDialogComponent {
  permisao?: IPermisao;

  protected permisaoService = inject(PermisaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.permisaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
