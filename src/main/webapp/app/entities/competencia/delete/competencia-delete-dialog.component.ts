import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICompetencia } from '../competencia.model';
import { CompetenciaService } from '../service/competencia.service';

@Component({
  standalone: true,
  templateUrl: './competencia-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CompetenciaDeleteDialogComponent {
  competencia?: ICompetencia;

  protected competenciaService = inject(CompetenciaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.competenciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
