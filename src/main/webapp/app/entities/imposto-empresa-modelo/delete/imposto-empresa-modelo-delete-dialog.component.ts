import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';
import { ImpostoEmpresaModeloService } from '../service/imposto-empresa-modelo.service';

@Component({
  standalone: true,
  templateUrl: './imposto-empresa-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ImpostoEmpresaModeloDeleteDialogComponent {
  impostoEmpresaModelo?: IImpostoEmpresaModelo;

  protected impostoEmpresaModeloService = inject(ImpostoEmpresaModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.impostoEmpresaModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
