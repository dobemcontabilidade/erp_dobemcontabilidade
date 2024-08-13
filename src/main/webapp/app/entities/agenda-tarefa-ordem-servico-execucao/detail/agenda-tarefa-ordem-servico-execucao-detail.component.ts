import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-agenda-tarefa-ordem-servico-execucao-detail',
  templateUrl: './agenda-tarefa-ordem-servico-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AgendaTarefaOrdemServicoExecucaoDetailComponent {
  agendaTarefaOrdemServicoExecucao = input<IAgendaTarefaOrdemServicoExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
