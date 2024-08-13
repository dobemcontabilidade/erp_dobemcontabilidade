import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAtividadeEmpresa } from '../atividade-empresa.model';
import { AtividadeEmpresaService } from '../service/atividade-empresa.service';

@Component({
  standalone: true,
  templateUrl: './atividade-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AtividadeEmpresaDeleteDialogComponent {
  atividadeEmpresa?: IAtividadeEmpresa;

  protected atividadeEmpresaService = inject(AtividadeEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.atividadeEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
