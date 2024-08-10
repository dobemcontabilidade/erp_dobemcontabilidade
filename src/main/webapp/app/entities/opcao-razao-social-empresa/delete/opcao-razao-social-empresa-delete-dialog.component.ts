import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';
import { OpcaoRazaoSocialEmpresaService } from '../service/opcao-razao-social-empresa.service';

@Component({
  standalone: true,
  templateUrl: './opcao-razao-social-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OpcaoRazaoSocialEmpresaDeleteDialogComponent {
  opcaoRazaoSocialEmpresa?: IOpcaoRazaoSocialEmpresa;

  protected opcaoRazaoSocialEmpresaService = inject(OpcaoRazaoSocialEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.opcaoRazaoSocialEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
