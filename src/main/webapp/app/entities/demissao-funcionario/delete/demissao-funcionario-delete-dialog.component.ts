import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDemissaoFuncionario } from '../demissao-funcionario.model';
import { DemissaoFuncionarioService } from '../service/demissao-funcionario.service';

@Component({
  standalone: true,
  templateUrl: './demissao-funcionario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DemissaoFuncionarioDeleteDialogComponent {
  demissaoFuncionario?: IDemissaoFuncionario;

  protected demissaoFuncionarioService = inject(DemissaoFuncionarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demissaoFuncionarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
