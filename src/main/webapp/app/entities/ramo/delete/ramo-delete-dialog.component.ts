import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRamo } from '../ramo.model';
import { RamoService } from '../service/ramo.service';

@Component({
  standalone: true,
  templateUrl: './ramo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RamoDeleteDialogComponent {
  ramo?: IRamo;

  protected ramoService = inject(RamoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ramoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
