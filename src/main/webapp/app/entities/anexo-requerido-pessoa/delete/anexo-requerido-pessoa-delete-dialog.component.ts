import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';
import { AnexoRequeridoPessoaService } from '../service/anexo-requerido-pessoa.service';

@Component({
  standalone: true,
  templateUrl: './anexo-requerido-pessoa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoRequeridoPessoaDeleteDialogComponent {
  anexoRequeridoPessoa?: IAnexoRequeridoPessoa;

  protected anexoRequeridoPessoaService = inject(AnexoRequeridoPessoaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoRequeridoPessoaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
