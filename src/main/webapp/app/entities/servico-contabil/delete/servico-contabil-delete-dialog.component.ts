import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IServicoContabil } from '../servico-contabil.model';
import { ServicoContabilService } from '../service/servico-contabil.service';

@Component({
  standalone: true,
  templateUrl: './servico-contabil-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServicoContabilDeleteDialogComponent {
  servicoContabil?: IServicoContabil;

  protected servicoContabilService = inject(ServicoContabilService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicoContabilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
