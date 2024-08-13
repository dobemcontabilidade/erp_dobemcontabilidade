import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';
import { GrupoAcessoUsuarioContadorService } from '../service/grupo-acesso-usuario-contador.service';

@Component({
  standalone: true,
  templateUrl: './grupo-acesso-usuario-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GrupoAcessoUsuarioContadorDeleteDialogComponent {
  grupoAcessoUsuarioContador?: IGrupoAcessoUsuarioContador;

  protected grupoAcessoUsuarioContadorService = inject(GrupoAcessoUsuarioContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoAcessoUsuarioContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
