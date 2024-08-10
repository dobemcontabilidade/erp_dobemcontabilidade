import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';
import { AreaContabilEmpresaService } from '../service/area-contabil-empresa.service';

@Component({
  standalone: true,
  templateUrl: './area-contabil-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AreaContabilEmpresaDeleteDialogComponent {
  areaContabilEmpresa?: IAreaContabilEmpresa;

  protected areaContabilEmpresaService = inject(AreaContabilEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.areaContabilEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
