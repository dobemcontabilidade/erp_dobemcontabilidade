import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRedeSocialEmpresa } from '../rede-social-empresa.model';
import { RedeSocialEmpresaService } from '../service/rede-social-empresa.service';

@Component({
  standalone: true,
  templateUrl: './rede-social-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RedeSocialEmpresaDeleteDialogComponent {
  redeSocialEmpresa?: IRedeSocialEmpresa;

  protected redeSocialEmpresaService = inject(RedeSocialEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.redeSocialEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
