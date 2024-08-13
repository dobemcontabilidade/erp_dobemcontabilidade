import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';
import { AnexoRequeridoServicoContabilService } from '../service/anexo-requerido-servico-contabil.service';

@Component({
  standalone: true,
  templateUrl: './anexo-requerido-servico-contabil-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoRequeridoServicoContabilDeleteDialogComponent {
  anexoRequeridoServicoContabil?: IAnexoRequeridoServicoContabil;

  protected anexoRequeridoServicoContabilService = inject(AnexoRequeridoServicoContabilService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoRequeridoServicoContabilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
