import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';
import { ServicoContabilEmpresaModeloService } from '../service/servico-contabil-empresa-modelo.service';

@Component({
  standalone: true,
  templateUrl: './servico-contabil-empresa-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServicoContabilEmpresaModeloDeleteDialogComponent {
  servicoContabilEmpresaModelo?: IServicoContabilEmpresaModelo;

  protected servicoContabilEmpresaModeloService = inject(ServicoContabilEmpresaModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicoContabilEmpresaModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
