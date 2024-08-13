import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IObservacaoCnae } from '../observacao-cnae.model';
import { ObservacaoCnaeService } from '../service/observacao-cnae.service';

@Component({
  standalone: true,
  templateUrl: './observacao-cnae-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ObservacaoCnaeDeleteDialogComponent {
  observacaoCnae?: IObservacaoCnae;

  protected observacaoCnaeService = inject(ObservacaoCnaeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.observacaoCnaeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
