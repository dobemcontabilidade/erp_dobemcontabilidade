import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICertificadoDigital } from '../certificado-digital.model';
import { CertificadoDigitalService } from '../service/certificado-digital.service';

@Component({
  standalone: true,
  templateUrl: './certificado-digital-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CertificadoDigitalDeleteDialogComponent {
  certificadoDigital?: ICertificadoDigital;

  protected certificadoDigitalService = inject(CertificadoDigitalService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.certificadoDigitalService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
