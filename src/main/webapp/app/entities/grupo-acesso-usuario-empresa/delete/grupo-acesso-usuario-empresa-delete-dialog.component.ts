import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';
import { GrupoAcessoUsuarioEmpresaService } from '../service/grupo-acesso-usuario-empresa.service';

@Component({
  standalone: true,
  templateUrl: './grupo-acesso-usuario-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GrupoAcessoUsuarioEmpresaDeleteDialogComponent {
  grupoAcessoUsuarioEmpresa?: IGrupoAcessoUsuarioEmpresa;

  protected grupoAcessoUsuarioEmpresaService = inject(GrupoAcessoUsuarioEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoAcessoUsuarioEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
