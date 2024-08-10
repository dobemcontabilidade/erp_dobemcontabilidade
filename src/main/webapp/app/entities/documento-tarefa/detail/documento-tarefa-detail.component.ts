import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDocumentoTarefa } from '../documento-tarefa.model';

@Component({
  standalone: true,
  selector: 'jhi-documento-tarefa-detail',
  templateUrl: './documento-tarefa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DocumentoTarefaDetailComponent {
  documentoTarefa = input<IDocumentoTarefa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
