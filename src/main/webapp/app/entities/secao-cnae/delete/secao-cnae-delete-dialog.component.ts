import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISecaoCnae } from '../secao-cnae.model';
import { SecaoCnaeService } from '../service/secao-cnae.service';

@Component({
  standalone: true,
  templateUrl: './secao-cnae-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SecaoCnaeDeleteDialogComponent {
  secaoCnae?: ISecaoCnae;

  protected secaoCnaeService = inject(SecaoCnaeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.secaoCnaeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
