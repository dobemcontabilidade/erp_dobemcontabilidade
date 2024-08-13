import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICobrancaEmpresa } from '../cobranca-empresa.model';
import { CobrancaEmpresaService } from '../service/cobranca-empresa.service';

@Component({
  standalone: true,
  templateUrl: './cobranca-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CobrancaEmpresaDeleteDialogComponent {
  cobrancaEmpresa?: ICobrancaEmpresa;

  protected cobrancaEmpresaService = inject(CobrancaEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cobrancaEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
