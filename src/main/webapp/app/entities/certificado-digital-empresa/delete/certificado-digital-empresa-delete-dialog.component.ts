import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';
import { CertificadoDigitalEmpresaService } from '../service/certificado-digital-empresa.service';

@Component({
  standalone: true,
  templateUrl: './certificado-digital-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CertificadoDigitalEmpresaDeleteDialogComponent {
  certificadoDigitalEmpresa?: ICertificadoDigitalEmpresa;

  protected certificadoDigitalEmpresaService = inject(CertificadoDigitalEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.certificadoDigitalEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
