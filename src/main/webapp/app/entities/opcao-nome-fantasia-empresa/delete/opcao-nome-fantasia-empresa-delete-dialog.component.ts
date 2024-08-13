import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOpcaoNomeFantasiaEmpresa } from '../opcao-nome-fantasia-empresa.model';
import { OpcaoNomeFantasiaEmpresaService } from '../service/opcao-nome-fantasia-empresa.service';

@Component({
  standalone: true,
  templateUrl: './opcao-nome-fantasia-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OpcaoNomeFantasiaEmpresaDeleteDialogComponent {
  opcaoNomeFantasiaEmpresa?: IOpcaoNomeFantasiaEmpresa;

  protected opcaoNomeFantasiaEmpresaService = inject(OpcaoNomeFantasiaEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.opcaoNomeFantasiaEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
