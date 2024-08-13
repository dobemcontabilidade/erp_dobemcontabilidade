import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAgendaContadorConfig } from '../agenda-contador-config.model';
import { AgendaContadorConfigService } from '../service/agenda-contador-config.service';

@Component({
  standalone: true,
  templateUrl: './agenda-contador-config-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AgendaContadorConfigDeleteDialogComponent {
  agendaContadorConfig?: IAgendaContadorConfig;

  protected agendaContadorConfigService = inject(AgendaContadorConfigService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agendaContadorConfigService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
