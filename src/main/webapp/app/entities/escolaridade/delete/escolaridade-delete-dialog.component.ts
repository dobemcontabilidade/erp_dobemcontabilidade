import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEscolaridade } from '../escolaridade.model';
import { EscolaridadeService } from '../service/escolaridade.service';

@Component({
  standalone: true,
  templateUrl: './escolaridade-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EscolaridadeDeleteDialogComponent {
  escolaridade?: IEscolaridade;

  protected escolaridadeService = inject(EscolaridadeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.escolaridadeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
