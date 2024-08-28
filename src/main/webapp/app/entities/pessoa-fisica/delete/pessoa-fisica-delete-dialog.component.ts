import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPessoaFisica } from '../pessoa-fisica.model';
import { PessoaFisicaService } from '../service/pessoa-fisica.service';

@Component({
  standalone: true,
  templateUrl: './pessoa-fisica-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PessoaFisicaDeleteDialogComponent {
  pessoaFisica?: IPessoaFisica;

  protected pessoaFisicaService = inject(PessoaFisicaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pessoaFisicaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
