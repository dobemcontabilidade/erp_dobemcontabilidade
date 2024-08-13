import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';
import { FuncionalidadeGrupoAcessoPadraoService } from '../service/funcionalidade-grupo-acesso-padrao.service';

@Component({
  standalone: true,
  templateUrl: './funcionalidade-grupo-acesso-padrao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuncionalidadeGrupoAcessoPadraoDeleteDialogComponent {
  funcionalidadeGrupoAcessoPadrao?: IFuncionalidadeGrupoAcessoPadrao;

  protected funcionalidadeGrupoAcessoPadraoService = inject(FuncionalidadeGrupoAcessoPadraoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.funcionalidadeGrupoAcessoPadraoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
