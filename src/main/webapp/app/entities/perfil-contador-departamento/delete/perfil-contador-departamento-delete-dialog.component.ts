import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPerfilContadorDepartamento } from '../perfil-contador-departamento.model';
import { PerfilContadorDepartamentoService } from '../service/perfil-contador-departamento.service';

@Component({
  standalone: true,
  templateUrl: './perfil-contador-departamento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PerfilContadorDepartamentoDeleteDialogComponent {
  perfilContadorDepartamento?: IPerfilContadorDepartamento;

  protected perfilContadorDepartamentoService = inject(PerfilContadorDepartamentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilContadorDepartamentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
