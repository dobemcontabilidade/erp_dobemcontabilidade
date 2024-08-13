import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoRequeridoEmpresa } from '../anexo-requerido-empresa.model';
import { AnexoRequeridoEmpresaService } from '../service/anexo-requerido-empresa.service';

@Component({
  standalone: true,
  templateUrl: './anexo-requerido-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoRequeridoEmpresaDeleteDialogComponent {
  anexoRequeridoEmpresa?: IAnexoRequeridoEmpresa;

  protected anexoRequeridoEmpresaService = inject(AnexoRequeridoEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoRequeridoEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
