import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';
import { ImpostoAPagarEmpresaService } from '../service/imposto-a-pagar-empresa.service';

@Component({
  standalone: true,
  templateUrl: './imposto-a-pagar-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ImpostoAPagarEmpresaDeleteDialogComponent {
  impostoAPagarEmpresa?: IImpostoAPagarEmpresa;

  protected impostoAPagarEmpresaService = inject(ImpostoAPagarEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.impostoAPagarEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
