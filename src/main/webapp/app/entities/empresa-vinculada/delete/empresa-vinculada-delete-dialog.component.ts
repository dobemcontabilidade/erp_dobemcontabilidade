import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEmpresaVinculada } from '../empresa-vinculada.model';
import { EmpresaVinculadaService } from '../service/empresa-vinculada.service';

@Component({
  standalone: true,
  templateUrl: './empresa-vinculada-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EmpresaVinculadaDeleteDialogComponent {
  empresaVinculada?: IEmpresaVinculada;

  protected empresaVinculadaService = inject(EmpresaVinculadaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empresaVinculadaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
