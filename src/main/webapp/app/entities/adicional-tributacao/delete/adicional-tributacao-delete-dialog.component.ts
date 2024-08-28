import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAdicionalTributacao } from '../adicional-tributacao.model';
import { AdicionalTributacaoService } from '../service/adicional-tributacao.service';

@Component({
  standalone: true,
  templateUrl: './adicional-tributacao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AdicionalTributacaoDeleteDialogComponent {
  adicionalTributacao?: IAdicionalTributacao;

  protected adicionalTributacaoService = inject(AdicionalTributacaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adicionalTributacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
