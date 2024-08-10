import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlanoContabil } from '../plano-contabil.model';
import { PlanoContabilService } from '../service/plano-contabil.service';

@Component({
  standalone: true,
  templateUrl: './plano-contabil-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlanoContabilDeleteDialogComponent {
  planoContabil?: IPlanoContabil;

  protected planoContabilService = inject(PlanoContabilService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoContabilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
