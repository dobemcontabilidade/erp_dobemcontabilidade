import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IUsuarioEmpresa } from '../usuario-empresa.model';
import { UsuarioEmpresaService } from '../service/usuario-empresa.service';

@Component({
  standalone: true,
  templateUrl: './usuario-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class UsuarioEmpresaDeleteDialogComponent {
  usuarioEmpresa?: IUsuarioEmpresa;

  protected usuarioEmpresaService = inject(UsuarioEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuarioEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
