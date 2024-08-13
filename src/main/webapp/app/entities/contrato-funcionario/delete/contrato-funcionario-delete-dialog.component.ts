import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContratoFuncionario } from '../contrato-funcionario.model';
import { ContratoFuncionarioService } from '../service/contrato-funcionario.service';

@Component({
  standalone: true,
  templateUrl: './contrato-funcionario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContratoFuncionarioDeleteDialogComponent {
  contratoFuncionario?: IContratoFuncionario;

  protected contratoFuncionarioService = inject(ContratoFuncionarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contratoFuncionarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
