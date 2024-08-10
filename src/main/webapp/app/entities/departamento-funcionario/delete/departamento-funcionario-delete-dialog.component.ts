import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDepartamentoFuncionario } from '../departamento-funcionario.model';
import { DepartamentoFuncionarioService } from '../service/departamento-funcionario.service';

@Component({
  standalone: true,
  templateUrl: './departamento-funcionario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DepartamentoFuncionarioDeleteDialogComponent {
  departamentoFuncionario?: IDepartamentoFuncionario;

  protected departamentoFuncionarioService = inject(DepartamentoFuncionarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.departamentoFuncionarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
