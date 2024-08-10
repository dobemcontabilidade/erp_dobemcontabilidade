import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPerfilRedeSocial } from '../perfil-rede-social.model';
import { PerfilRedeSocialService } from '../service/perfil-rede-social.service';

@Component({
  standalone: true,
  templateUrl: './perfil-rede-social-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PerfilRedeSocialDeleteDialogComponent {
  perfilRedeSocial?: IPerfilRedeSocial;

  protected perfilRedeSocialService = inject(PerfilRedeSocialService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilRedeSocialService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
