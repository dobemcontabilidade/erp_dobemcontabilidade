import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISubtarefa } from '../subtarefa.model';
import { SubtarefaService } from '../service/subtarefa.service';

@Component({
  standalone: true,
  templateUrl: './subtarefa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SubtarefaDeleteDialogComponent {
  subtarefa?: ISubtarefa;

  protected subtarefaService = inject(SubtarefaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subtarefaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
