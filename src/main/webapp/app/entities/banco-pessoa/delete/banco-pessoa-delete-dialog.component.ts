import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBancoPessoa } from '../banco-pessoa.model';
import { BancoPessoaService } from '../service/banco-pessoa.service';

@Component({
  standalone: true,
  templateUrl: './banco-pessoa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BancoPessoaDeleteDialogComponent {
  bancoPessoa?: IBancoPessoa;

  protected bancoPessoaService = inject(BancoPessoaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bancoPessoaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
