import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlanoContaAzul } from '../plano-conta-azul.model';
import { PlanoContaAzulService } from '../service/plano-conta-azul.service';

@Component({
  standalone: true,
  templateUrl: './plano-conta-azul-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlanoContaAzulDeleteDialogComponent {
  planoContaAzul?: IPlanoContaAzul;

  protected planoContaAzulService = inject(PlanoContaAzulService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoContaAzulService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
