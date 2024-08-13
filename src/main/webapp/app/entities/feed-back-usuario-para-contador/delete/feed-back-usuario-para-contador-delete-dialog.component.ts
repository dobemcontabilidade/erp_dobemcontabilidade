import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';
import { FeedBackUsuarioParaContadorService } from '../service/feed-back-usuario-para-contador.service';

@Component({
  standalone: true,
  templateUrl: './feed-back-usuario-para-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FeedBackUsuarioParaContadorDeleteDialogComponent {
  feedBackUsuarioParaContador?: IFeedBackUsuarioParaContador;

  protected feedBackUsuarioParaContadorService = inject(FeedBackUsuarioParaContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feedBackUsuarioParaContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
