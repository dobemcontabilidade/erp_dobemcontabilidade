import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDependentesFuncionario } from '../dependentes-funcionario.model';
import { DependentesFuncionarioService } from '../service/dependentes-funcionario.service';

@Component({
  standalone: true,
  templateUrl: './dependentes-funcionario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DependentesFuncionarioDeleteDialogComponent {
  dependentesFuncionario?: IDependentesFuncionario;

  protected dependentesFuncionarioService = inject(DependentesFuncionarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dependentesFuncionarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
