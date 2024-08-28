import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAdicionalEnquadramento } from '../adicional-enquadramento.model';
import { AdicionalEnquadramentoService } from '../service/adicional-enquadramento.service';

@Component({
  standalone: true,
  templateUrl: './adicional-enquadramento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AdicionalEnquadramentoDeleteDialogComponent {
  adicionalEnquadramento?: IAdicionalEnquadramento;

  protected adicionalEnquadramentoService = inject(AdicionalEnquadramentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adicionalEnquadramentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
