import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';
import { FeedBackContadorParaUsuarioService } from '../service/feed-back-contador-para-usuario.service';

@Component({
  standalone: true,
  templateUrl: './feed-back-contador-para-usuario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FeedBackContadorParaUsuarioDeleteDialogComponent {
  feedBackContadorParaUsuario?: IFeedBackContadorParaUsuario;

  protected feedBackContadorParaUsuarioService = inject(FeedBackContadorParaUsuarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feedBackContadorParaUsuarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
