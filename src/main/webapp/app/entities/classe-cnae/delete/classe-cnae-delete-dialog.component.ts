import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IClasseCnae } from '../classe-cnae.model';
import { ClasseCnaeService } from '../service/classe-cnae.service';

@Component({
  standalone: true,
  templateUrl: './classe-cnae-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ClasseCnaeDeleteDialogComponent {
  classeCnae?: IClasseCnae;

  protected classeCnaeService = inject(ClasseCnaeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classeCnaeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
