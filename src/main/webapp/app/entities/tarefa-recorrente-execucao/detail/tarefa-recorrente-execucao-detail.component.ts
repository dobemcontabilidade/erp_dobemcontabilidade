import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-recorrente-execucao-detail',
  templateUrl: './tarefa-recorrente-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaRecorrenteExecucaoDetailComponent {
  tarefaRecorrenteExecucao = input<ITarefaRecorrenteExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
