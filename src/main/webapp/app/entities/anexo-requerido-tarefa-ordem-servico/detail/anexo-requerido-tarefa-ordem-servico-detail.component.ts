import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-tarefa-ordem-servico-detail',
  templateUrl: './anexo-requerido-tarefa-ordem-servico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AnexoRequeridoTarefaOrdemServicoDetailComponent {
  anexoRequeridoTarefaOrdemServico = input<IAnexoRequeridoTarefaOrdemServico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
