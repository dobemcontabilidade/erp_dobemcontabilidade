import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-ordem-servico-execucao-detail',
  templateUrl: './tarefa-ordem-servico-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaOrdemServicoExecucaoDetailComponent {
  tarefaOrdemServicoExecucao = input<ITarefaOrdemServicoExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
