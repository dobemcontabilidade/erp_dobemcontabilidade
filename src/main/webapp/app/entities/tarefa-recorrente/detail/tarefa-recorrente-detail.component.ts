import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefaRecorrente } from '../tarefa-recorrente.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-recorrente-detail',
  templateUrl: './tarefa-recorrente-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaRecorrenteDetailComponent {
  tarefaRecorrente = input<ITarefaRecorrente | null>(null);

  previousState(): void {
    window.history.back();
  }
}
