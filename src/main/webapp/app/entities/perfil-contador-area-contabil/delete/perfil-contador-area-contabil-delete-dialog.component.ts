import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';
import { PerfilContadorAreaContabilService } from '../service/perfil-contador-area-contabil.service';

@Component({
  standalone: true,
  templateUrl: './perfil-contador-area-contabil-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PerfilContadorAreaContabilDeleteDialogComponent {
  perfilContadorAreaContabil?: IPerfilContadorAreaContabil;

  protected perfilContadorAreaContabilService = inject(PerfilContadorAreaContabilService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilContadorAreaContabilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
