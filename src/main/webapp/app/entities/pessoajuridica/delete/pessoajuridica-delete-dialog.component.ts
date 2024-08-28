import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPessoajuridica } from '../pessoajuridica.model';
import { PessoajuridicaService } from '../service/pessoajuridica.service';

@Component({
  standalone: true,
  templateUrl: './pessoajuridica-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PessoajuridicaDeleteDialogComponent {
  pessoajuridica?: IPessoajuridica;

  protected pessoajuridicaService = inject(PessoajuridicaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pessoajuridicaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
