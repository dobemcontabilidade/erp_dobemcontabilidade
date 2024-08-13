import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from '../service/criterio-avaliacao-ator.service';

@Component({
  standalone: true,
  templateUrl: './criterio-avaliacao-ator-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CriterioAvaliacaoAtorDeleteDialogComponent {
  criterioAvaliacaoAtor?: ICriterioAvaliacaoAtor;

  protected criterioAvaliacaoAtorService = inject(CriterioAvaliacaoAtorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.criterioAvaliacaoAtorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
