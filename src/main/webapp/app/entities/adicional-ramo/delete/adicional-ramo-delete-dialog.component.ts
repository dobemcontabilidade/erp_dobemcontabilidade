import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAdicionalRamo } from '../adicional-ramo.model';
import { AdicionalRamoService } from '../service/adicional-ramo.service';

@Component({
  standalone: true,
  templateUrl: './adicional-ramo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AdicionalRamoDeleteDialogComponent {
  adicionalRamo?: IAdicionalRamo;

  protected adicionalRamoService = inject(AdicionalRamoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adicionalRamoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
