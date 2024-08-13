import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAssinaturaEmpresa } from '../assinatura-empresa.model';
import { AssinaturaEmpresaService } from '../service/assinatura-empresa.service';

@Component({
  standalone: true,
  templateUrl: './assinatura-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AssinaturaEmpresaDeleteDialogComponent {
  assinaturaEmpresa?: IAssinaturaEmpresa;

  protected assinaturaEmpresaService = inject(AssinaturaEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assinaturaEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
