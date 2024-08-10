import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEnderecoEmpresa } from '../endereco-empresa.model';
import { EnderecoEmpresaService } from '../service/endereco-empresa.service';

@Component({
  standalone: true,
  templateUrl: './endereco-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EnderecoEmpresaDeleteDialogComponent {
  enderecoEmpresa?: IEnderecoEmpresa;

  protected enderecoEmpresaService = inject(EnderecoEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enderecoEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
