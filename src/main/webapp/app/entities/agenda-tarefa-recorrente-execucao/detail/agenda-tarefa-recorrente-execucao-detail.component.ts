import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-agenda-tarefa-recorrente-execucao-detail',
  templateUrl: './agenda-tarefa-recorrente-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AgendaTarefaRecorrenteExecucaoDetailComponent {
  agendaTarefaRecorrenteExecucao = input<IAgendaTarefaRecorrenteExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
