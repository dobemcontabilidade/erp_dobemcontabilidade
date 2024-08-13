import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';
import { FuncionalidadeGrupoAcessoEmpresaService } from '../service/funcionalidade-grupo-acesso-empresa.service';

@Component({
  standalone: true,
  templateUrl: './funcionalidade-grupo-acesso-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuncionalidadeGrupoAcessoEmpresaDeleteDialogComponent {
  funcionalidadeGrupoAcessoEmpresa?: IFuncionalidadeGrupoAcessoEmpresa;

  protected funcionalidadeGrupoAcessoEmpresaService = inject(FuncionalidadeGrupoAcessoEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.funcionalidadeGrupoAcessoEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
