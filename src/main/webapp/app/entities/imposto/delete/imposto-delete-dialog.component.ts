import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IImposto } from '../imposto.model';
import { ImpostoService } from '../service/imposto.service';

@Component({
  standalone: true,
  templateUrl: './imposto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ImpostoDeleteDialogComponent {
  imposto?: IImposto;

  protected impostoService = inject(ImpostoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.impostoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
