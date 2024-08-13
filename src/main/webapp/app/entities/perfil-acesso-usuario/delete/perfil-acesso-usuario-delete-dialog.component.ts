import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';
import { PerfilAcessoUsuarioService } from '../service/perfil-acesso-usuario.service';

@Component({
  standalone: true,
  templateUrl: './perfil-acesso-usuario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PerfilAcessoUsuarioDeleteDialogComponent {
  perfilAcessoUsuario?: IPerfilAcessoUsuario;

  protected perfilAcessoUsuarioService = inject(PerfilAcessoUsuarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilAcessoUsuarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
