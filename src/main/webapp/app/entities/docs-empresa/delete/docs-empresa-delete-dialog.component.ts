import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDocsEmpresa } from '../docs-empresa.model';
import { DocsEmpresaService } from '../service/docs-empresa.service';

@Component({
  standalone: true,
  templateUrl: './docs-empresa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DocsEmpresaDeleteDialogComponent {
  docsEmpresa?: IDocsEmpresa;

  protected docsEmpresaService = inject(DocsEmpresaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.docsEmpresaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
