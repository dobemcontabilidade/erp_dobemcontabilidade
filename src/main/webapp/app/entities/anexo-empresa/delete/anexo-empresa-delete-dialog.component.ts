import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAnexoEmpresa } from '../anexo-empresa.model';
import { AnexoEmpresaService } from '../service/anexo-empresa.service';

@Component({
  standalone: true,
  templateUrl: './anexo-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AnexoEmpresaDeleteDialogComponent {
  anexoEmpresa?: IAnexoEmpresa;

  protected anexoEmpresaService = inject(AnexoEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
