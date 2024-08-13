import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISubclasseCnae } from '../subclasse-cnae.model';
import { SubclasseCnaeService } from '../service/subclasse-cnae.service';

@Component({
  standalone: true,
  templateUrl: './subclasse-cnae-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SubclasseCnaeDeleteDialogComponent {
  subclasseCnae?: ISubclasseCnae;

  protected subclasseCnaeService = inject(SubclasseCnaeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subclasseCnaeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
