import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDepartamentoEmpresa } from '../departamento-empresa.model';
import { DepartamentoEmpresaService } from '../service/departamento-empresa.service';

@Component({
  standalone: true,
  templateUrl: './departamento-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DepartamentoEmpresaDeleteDialogComponent {
  departamentoEmpresa?: IDepartamentoEmpresa;

  protected departamentoEmpresaService = inject(DepartamentoEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.departamentoEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
