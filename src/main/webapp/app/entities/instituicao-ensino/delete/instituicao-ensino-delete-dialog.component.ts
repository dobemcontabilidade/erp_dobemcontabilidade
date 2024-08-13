import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { InstituicaoEnsinoService } from '../service/instituicao-ensino.service';

@Component({
  standalone: true,
  templateUrl: './instituicao-ensino-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InstituicaoEnsinoDeleteDialogComponent {
  instituicaoEnsino?: IInstituicaoEnsino;

  protected instituicaoEnsinoService = inject(InstituicaoEnsinoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.instituicaoEnsinoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
