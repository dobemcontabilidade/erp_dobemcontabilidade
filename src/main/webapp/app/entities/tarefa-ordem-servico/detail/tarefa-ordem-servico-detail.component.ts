import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefaOrdemServico } from '../tarefa-ordem-servico.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-ordem-servico-detail',
  templateUrl: './tarefa-ordem-servico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaOrdemServicoDetailComponent {
  tarefaOrdemServico = input<ITarefaOrdemServico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
