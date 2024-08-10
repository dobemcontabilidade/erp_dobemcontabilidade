import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDocumentoTarefa } from '../documento-tarefa.model';
import { DocumentoTarefaService } from '../service/documento-tarefa.service';

@Component({
  standalone: true,
  templateUrl: './documento-tarefa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DocumentoTarefaDeleteDialogComponent {
  documentoTarefa?: IDocumentoTarefa;

  protected documentoTarefaService = inject(DocumentoTarefaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentoTarefaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
