import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEsfera } from '../esfera.model';
import { EsferaService } from '../service/esfera.service';

@Component({
  standalone: true,
  templateUrl: './esfera-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EsferaDeleteDialogComponent {
  esfera?: IEsfera;

  protected esferaService = inject(EsferaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.esferaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
