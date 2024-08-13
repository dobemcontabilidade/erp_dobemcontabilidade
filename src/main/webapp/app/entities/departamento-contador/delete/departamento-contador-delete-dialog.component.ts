import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDepartamentoContador } from '../departamento-contador.model';
import { DepartamentoContadorService } from '../service/departamento-contador.service';

@Component({
  standalone: true,
  templateUrl: './departamento-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DepartamentoContadorDeleteDialogComponent {
  departamentoContador?: IDepartamentoContador;

  protected departamentoContadorService = inject(DepartamentoContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.departamentoContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
