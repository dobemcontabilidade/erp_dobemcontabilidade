import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from '../service/grupo-acesso-padrao.service';

@Component({
  standalone: true,
  templateUrl: './grupo-acesso-padrao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GrupoAcessoPadraoDeleteDialogComponent {
  grupoAcessoPadrao?: IGrupoAcessoPadrao;

  protected grupoAcessoPadraoService = inject(GrupoAcessoPadraoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoAcessoPadraoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
