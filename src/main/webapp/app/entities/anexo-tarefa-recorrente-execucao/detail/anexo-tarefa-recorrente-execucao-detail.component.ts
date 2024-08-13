import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-anexo-tarefa-recorrente-execucao-detail',
  templateUrl: './anexo-tarefa-recorrente-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AnexoTarefaRecorrenteExecucaoDetailComponent {
  anexoTarefaRecorrenteExecucao = input<IAnexoTarefaRecorrenteExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
