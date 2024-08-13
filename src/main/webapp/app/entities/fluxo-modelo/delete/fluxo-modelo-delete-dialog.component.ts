import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFluxoModelo } from '../fluxo-modelo.model';
import { FluxoModeloService } from '../service/fluxo-modelo.service';

@Component({
  standalone: true,
  templateUrl: './fluxo-modelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FluxoModeloDeleteDialogComponent {
  fluxoModelo?: IFluxoModelo;

  protected fluxoModeloService = inject(FluxoModeloService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fluxoModeloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
