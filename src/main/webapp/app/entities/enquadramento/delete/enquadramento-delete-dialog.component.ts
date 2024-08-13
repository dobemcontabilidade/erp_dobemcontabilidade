import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEnquadramento } from '../enquadramento.model';
import { EnquadramentoService } from '../service/enquadramento.service';

@Component({
  standalone: true,
  templateUrl: './enquadramento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EnquadramentoDeleteDialogComponent {
  enquadramento?: IEnquadramento;

  protected enquadramentoService = inject(EnquadramentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enquadramentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
