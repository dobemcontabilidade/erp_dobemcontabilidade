import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from '../service/servico-contabil-assinatura-empresa.service';

@Component({
  standalone: true,
  templateUrl: './servico-contabil-assinatura-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServicoContabilAssinaturaEmpresaDeleteDialogComponent {
  servicoContabilAssinaturaEmpresa?: IServicoContabilAssinaturaEmpresa;

  protected servicoContabilAssinaturaEmpresaService = inject(ServicoContabilAssinaturaEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicoContabilAssinaturaEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
