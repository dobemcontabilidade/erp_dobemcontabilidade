import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDocsPessoa } from '../docs-pessoa.model';
import { DocsPessoaService } from '../service/docs-pessoa.service';

@Component({
  standalone: true,
  templateUrl: './docs-pessoa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DocsPessoaDeleteDialogComponent {
  docsPessoa?: IDocsPessoa;

  protected docsPessoaService = inject(DocsPessoaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.docsPessoaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
