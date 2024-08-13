import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IParcelaImpostoAPagar } from '../parcela-imposto-a-pagar.model';
import { ParcelaImpostoAPagarService } from '../service/parcela-imposto-a-pagar.service';

@Component({
  standalone: true,
  templateUrl: './parcela-imposto-a-pagar-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ParcelaImpostoAPagarDeleteDialogComponent {
  parcelaImpostoAPagar?: IParcelaImpostoAPagar;

  protected parcelaImpostoAPagarService = inject(ParcelaImpostoAPagarService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parcelaImpostoAPagarService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
