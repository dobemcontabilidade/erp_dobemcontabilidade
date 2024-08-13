import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';
import { CalculoPlanoAssinaturaService } from '../service/calculo-plano-assinatura.service';

@Component({
  standalone: true,
  templateUrl: './calculo-plano-assinatura-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CalculoPlanoAssinaturaDeleteDialogComponent {
  calculoPlanoAssinatura?: ICalculoPlanoAssinatura;

  protected calculoPlanoAssinaturaService = inject(CalculoPlanoAssinaturaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.calculoPlanoAssinaturaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
