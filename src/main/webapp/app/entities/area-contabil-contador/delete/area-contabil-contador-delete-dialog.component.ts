import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAreaContabilContador } from '../area-contabil-contador.model';
import { AreaContabilContadorService } from '../service/area-contabil-contador.service';

@Component({
  standalone: true,
  templateUrl: './area-contabil-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AreaContabilContadorDeleteDialogComponent {
  areaContabilContador?: IAreaContabilContador;

  protected areaContabilContadorService = inject(AreaContabilContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.areaContabilContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
