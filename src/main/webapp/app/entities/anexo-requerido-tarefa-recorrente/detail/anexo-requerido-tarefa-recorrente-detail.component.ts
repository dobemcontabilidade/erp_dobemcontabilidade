import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-tarefa-recorrente-detail',
  templateUrl: './anexo-requerido-tarefa-recorrente-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AnexoRequeridoTarefaRecorrenteDetailComponent {
  anexoRequeridoTarefaRecorrente = input<IAnexoRequeridoTarefaRecorrente | null>(null);

  previousState(): void {
    window.history.back();
  }
}
