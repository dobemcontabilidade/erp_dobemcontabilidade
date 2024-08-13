import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPerfilAcesso } from '../perfil-acesso.model';
import { PerfilAcessoService } from '../service/perfil-acesso.service';

@Component({
  standalone: true,
  templateUrl: './perfil-acesso-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PerfilAcessoDeleteDialogComponent {
  perfilAcesso?: IPerfilAcesso;

  protected perfilAcessoService = inject(PerfilAcessoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilAcessoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
