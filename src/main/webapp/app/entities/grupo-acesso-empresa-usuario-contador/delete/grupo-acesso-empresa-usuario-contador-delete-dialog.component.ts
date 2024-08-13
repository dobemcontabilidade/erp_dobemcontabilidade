import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';
import { GrupoAcessoEmpresaUsuarioContadorService } from '../service/grupo-acesso-empresa-usuario-contador.service';

@Component({
  standalone: true,
  templateUrl: './grupo-acesso-empresa-usuario-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GrupoAcessoEmpresaUsuarioContadorDeleteDialogComponent {
  grupoAcessoEmpresaUsuarioContador?: IGrupoAcessoEmpresaUsuarioContador;

  protected grupoAcessoEmpresaUsuarioContadorService = inject(GrupoAcessoEmpresaUsuarioContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoAcessoEmpresaUsuarioContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
