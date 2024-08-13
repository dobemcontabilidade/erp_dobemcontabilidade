import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProfissao } from '../profissao.model';
import { ProfissaoService } from '../service/profissao.service';

@Component({
  standalone: true,
  templateUrl: './profissao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProfissaoDeleteDialogComponent {
  profissao?: IProfissao;

  protected profissaoService = inject(ProfissaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.profissaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
