import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITermoAdesaoEmpresa } from '../termo-adesao-empresa.model';
import { TermoAdesaoEmpresaService } from '../service/termo-adesao-empresa.service';

@Component({
  standalone: true,
  templateUrl: './termo-adesao-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TermoAdesaoEmpresaDeleteDialogComponent {
  termoAdesaoEmpresa?: ITermoAdesaoEmpresa;

  protected termoAdesaoEmpresaService = inject(TermoAdesaoEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.termoAdesaoEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
