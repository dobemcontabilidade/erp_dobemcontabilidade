import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEscolaridadePessoa } from '../escolaridade-pessoa.model';
import { EscolaridadePessoaService } from '../service/escolaridade-pessoa.service';

@Component({
  standalone: true,
  templateUrl: './escolaridade-pessoa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EscolaridadePessoaDeleteDialogComponent {
  escolaridadePessoa?: IEscolaridadePessoa;

  protected escolaridadePessoaService = inject(EscolaridadePessoaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.escolaridadePessoaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
