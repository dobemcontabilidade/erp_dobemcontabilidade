import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITermoAdesaoContador } from '../termo-adesao-contador.model';
import { TermoAdesaoContadorService } from '../service/termo-adesao-contador.service';

@Component({
  standalone: true,
  templateUrl: './termo-adesao-contador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TermoAdesaoContadorDeleteDialogComponent {
  termoAdesaoContador?: ITermoAdesaoContador;

  protected termoAdesaoContadorService = inject(TermoAdesaoContadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.termoAdesaoContadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
