import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDescontoPlanoContaAzul } from '../desconto-plano-conta-azul.model';
import { DescontoPlanoContaAzulService } from '../service/desconto-plano-conta-azul.service';

@Component({
  standalone: true,
  templateUrl: './desconto-plano-conta-azul-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DescontoPlanoContaAzulDeleteDialogComponent {
  descontoPlanoContaAzul?: IDescontoPlanoContaAzul;

  protected descontoPlanoContaAzulService = inject(DescontoPlanoContaAzulService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.descontoPlanoContaAzulService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
