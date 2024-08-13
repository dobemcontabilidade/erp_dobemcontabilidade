import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IModulo } from '../modulo.model';
import { ModuloService } from '../service/modulo.service';

@Component({
  standalone: true,
  templateUrl: './modulo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ModuloDeleteDialogComponent {
  modulo?: IModulo;

  protected moduloService = inject(ModuloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moduloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
