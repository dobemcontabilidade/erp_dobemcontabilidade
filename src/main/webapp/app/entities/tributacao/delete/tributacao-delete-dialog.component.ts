import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITributacao } from '../tributacao.model';
import { TributacaoService } from '../service/tributacao.service';

@Component({
  standalone: true,
  templateUrl: './tributacao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TributacaoDeleteDialogComponent {
  tributacao?: ITributacao;

  protected tributacaoService = inject(TributacaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tributacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
