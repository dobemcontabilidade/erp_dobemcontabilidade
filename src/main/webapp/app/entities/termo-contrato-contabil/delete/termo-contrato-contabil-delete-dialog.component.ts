import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITermoContratoContabil } from '../termo-contrato-contabil.model';
import { TermoContratoContabilService } from '../service/termo-contrato-contabil.service';

@Component({
  standalone: true,
  templateUrl: './termo-contrato-contabil-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TermoContratoContabilDeleteDialogComponent {
  termoContratoContabil?: ITermoContratoContabil;

  protected termoContratoContabilService = inject(TermoContratoContabilService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.termoContratoContabilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
