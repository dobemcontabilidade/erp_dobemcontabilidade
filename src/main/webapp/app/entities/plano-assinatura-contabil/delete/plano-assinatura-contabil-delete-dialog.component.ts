import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from '../service/plano-assinatura-contabil.service';

@Component({
  standalone: true,
  templateUrl: './plano-assinatura-contabil-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlanoAssinaturaContabilDeleteDialogComponent {
  planoAssinaturaContabil?: IPlanoAssinaturaContabil;

  protected planoAssinaturaContabilService = inject(PlanoAssinaturaContabilService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoAssinaturaContabilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
