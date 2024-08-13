import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEmpresaModelo } from '../empresa-modelo.model';
import { EmpresaModeloService } from '../service/empresa-modelo.service';

@Component({
  standalone: true,
  templateUrl: './empresa-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EmpresaModeloDeleteDialogComponent {
  empresaModelo?: IEmpresaModelo;

  protected empresaModeloService = inject(EmpresaModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empresaModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
