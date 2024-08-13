import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFluxoExecucao } from '../fluxo-execucao.model';
import { FluxoExecucaoService } from '../service/fluxo-execucao.service';

@Component({
  standalone: true,
  templateUrl: './fluxo-execucao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FluxoExecucaoDeleteDialogComponent {
  fluxoExecucao?: IFluxoExecucao;

  protected fluxoExecucaoService = inject(FluxoExecucaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fluxoExecucaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
