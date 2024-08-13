import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuncionalidade } from '../funcionalidade.model';
import { FuncionalidadeService } from '../service/funcionalidade.service';

@Component({
  standalone: true,
  templateUrl: './funcionalidade-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuncionalidadeDeleteDialogComponent {
  funcionalidade?: IFuncionalidade;

  protected funcionalidadeService = inject(FuncionalidadeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.funcionalidadeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
