import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IUsuarioGestao } from '../usuario-gestao.model';
import { UsuarioGestaoService } from '../service/usuario-gestao.service';

@Component({
  standalone: true,
  templateUrl: './usuario-gestao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class UsuarioGestaoDeleteDialogComponent {
  usuarioGestao?: IUsuarioGestao;

  protected usuarioGestaoService = inject(UsuarioGestaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuarioGestaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
