import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ISubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';

@Component({
  standalone: true,
  selector: 'jhi-sub-tarefa-ordem-servico-detail',
  templateUrl: './sub-tarefa-ordem-servico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SubTarefaOrdemServicoDetailComponent {
  subTarefaOrdemServico = input<ISubTarefaOrdemServico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
