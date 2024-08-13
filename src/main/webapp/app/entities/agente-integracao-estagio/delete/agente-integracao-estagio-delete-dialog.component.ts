import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';
import { AgenteIntegracaoEstagioService } from '../service/agente-integracao-estagio.service';

@Component({
  standalone: true,
  templateUrl: './agente-integracao-estagio-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AgenteIntegracaoEstagioDeleteDialogComponent {
  agenteIntegracaoEstagio?: IAgenteIntegracaoEstagio;

  protected agenteIntegracaoEstagioService = inject(AgenteIntegracaoEstagioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agenteIntegracaoEstagioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
