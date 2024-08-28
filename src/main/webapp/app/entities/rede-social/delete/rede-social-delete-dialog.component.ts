import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRedeSocial } from '../rede-social.model';
import { RedeSocialService } from '../service/rede-social.service';

@Component({
  standalone: true,
  templateUrl: './rede-social-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RedeSocialDeleteDialogComponent {
  redeSocial?: IRedeSocial;

  protected redeSocialService = inject(RedeSocialService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.redeSocialService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
