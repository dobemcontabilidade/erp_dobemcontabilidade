import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISocio } from '../socio.model';
import { SocioService } from '../service/socio.service';

@Component({
  standalone: true,
  templateUrl: './socio-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SocioDeleteDialogComponent {
  socio?: ISocio;

  protected socioService = inject(SocioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.socioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
