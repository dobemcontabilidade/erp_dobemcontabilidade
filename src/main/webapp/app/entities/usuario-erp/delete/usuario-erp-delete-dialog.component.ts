import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IUsuarioErp } from '../usuario-erp.model';
import { UsuarioErpService } from '../service/usuario-erp.service';

@Component({
  standalone: true,
  templateUrl: './usuario-erp-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class UsuarioErpDeleteDialogComponent {
  usuarioErp?: IUsuarioErp;

  protected usuarioErpService = inject(UsuarioErpService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuarioErpService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
