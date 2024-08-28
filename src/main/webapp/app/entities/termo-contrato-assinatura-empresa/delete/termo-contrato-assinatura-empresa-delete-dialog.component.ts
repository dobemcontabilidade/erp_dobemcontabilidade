import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';
import { TermoContratoAssinaturaEmpresaService } from '../service/termo-contrato-assinatura-empresa.service';

@Component({
  standalone: true,
  templateUrl: './termo-contrato-assinatura-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TermoContratoAssinaturaEmpresaDeleteDialogComponent {
  termoContratoAssinaturaEmpresa?: ITermoContratoAssinaturaEmpresa;

  protected termoContratoAssinaturaEmpresaService = inject(TermoContratoAssinaturaEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.termoContratoAssinaturaEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
