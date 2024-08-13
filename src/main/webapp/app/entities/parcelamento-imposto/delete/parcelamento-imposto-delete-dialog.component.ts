import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IParcelamentoImposto } from '../parcelamento-imposto.model';
import { ParcelamentoImpostoService } from '../service/parcelamento-imposto.service';

@Component({
  standalone: true,
  templateUrl: './parcelamento-imposto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ParcelamentoImpostoDeleteDialogComponent {
  parcelamentoImposto?: IParcelamentoImposto;

  protected parcelamentoImpostoService = inject(ParcelamentoImpostoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parcelamentoImpostoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
