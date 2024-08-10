import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IValorBaseRamo } from '../valor-base-ramo.model';
import { ValorBaseRamoService } from '../service/valor-base-ramo.service';

@Component({
  standalone: true,
  templateUrl: './valor-base-ramo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ValorBaseRamoDeleteDialogComponent {
  valorBaseRamo?: IValorBaseRamo;

  protected valorBaseRamoService = inject(ValorBaseRamoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.valorBaseRamoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
