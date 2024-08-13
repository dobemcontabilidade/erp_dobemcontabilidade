import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';
import { AreaContabilAssinaturaEmpresaService } from '../service/area-contabil-assinatura-empresa.service';

@Component({
  standalone: true,
  templateUrl: './area-contabil-assinatura-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AreaContabilAssinaturaEmpresaDeleteDialogComponent {
  areaContabilAssinaturaEmpresa?: IAreaContabilAssinaturaEmpresa;

  protected areaContabilAssinaturaEmpresaService = inject(AreaContabilAssinaturaEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.areaContabilAssinaturaEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
