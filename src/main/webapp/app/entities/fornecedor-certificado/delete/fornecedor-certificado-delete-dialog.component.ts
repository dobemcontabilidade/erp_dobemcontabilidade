import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFornecedorCertificado } from '../fornecedor-certificado.model';
import { FornecedorCertificadoService } from '../service/fornecedor-certificado.service';

@Component({
  standalone: true,
  templateUrl: './fornecedor-certificado-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FornecedorCertificadoDeleteDialogComponent {
  fornecedorCertificado?: IFornecedorCertificado;

  protected fornecedorCertificadoService = inject(FornecedorCertificadoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fornecedorCertificadoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
