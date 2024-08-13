import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEnderecoPessoa } from '../endereco-pessoa.model';
import { EnderecoPessoaService } from '../service/endereco-pessoa.service';

@Component({
  standalone: true,
  templateUrl: './endereco-pessoa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EnderecoPessoaDeleteDialogComponent {
  enderecoPessoa?: IEnderecoPessoa;

  protected enderecoPessoaService = inject(EnderecoPessoaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enderecoPessoaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
