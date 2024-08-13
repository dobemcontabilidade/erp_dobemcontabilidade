import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICriterioAvaliacao } from '../criterio-avaliacao.model';
import { CriterioAvaliacaoService } from '../service/criterio-avaliacao.service';

@Component({
  standalone: true,
  templateUrl: './criterio-avaliacao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CriterioAvaliacaoDeleteDialogComponent {
  criterioAvaliacao?: ICriterioAvaliacao;

  protected criterioAvaliacaoService = inject(CriterioAvaliacaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.criterioAvaliacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
