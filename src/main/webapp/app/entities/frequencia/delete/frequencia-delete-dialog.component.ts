import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFrequencia } from '../frequencia.model';
import { FrequenciaService } from '../service/frequencia.service';

@Component({
  standalone: true,
  templateUrl: './frequencia-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FrequenciaDeleteDialogComponent {
  frequencia?: IFrequencia;

  protected frequenciaService = inject(FrequenciaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.frequenciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
