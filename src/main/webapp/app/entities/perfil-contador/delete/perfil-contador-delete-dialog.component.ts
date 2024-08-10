import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPerfilContador } from '../perfil-contador.model';
import { PerfilContadorService } from '../service/perfil-contador.service';

@Component({
  standalone: true,
  templateUrl: './perfil-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PerfilContadorDeleteDialogComponent {
  perfilContador?: IPerfilContador;

  protected perfilContadorService = inject(PerfilContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
