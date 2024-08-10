import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDescontoPlanoContabil } from '../desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from '../service/desconto-plano-contabil.service';

@Component({
  standalone: true,
  templateUrl: './desconto-plano-contabil-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DescontoPlanoContabilDeleteDialogComponent {
  descontoPlanoContabil?: IDescontoPlanoContabil;

  protected descontoPlanoContabilService = inject(DescontoPlanoContabilService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.descontoPlanoContabilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
