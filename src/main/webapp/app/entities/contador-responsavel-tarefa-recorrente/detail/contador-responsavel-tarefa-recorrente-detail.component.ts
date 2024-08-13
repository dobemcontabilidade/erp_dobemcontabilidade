import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContadorResponsavelTarefaRecorrente } from '../contador-responsavel-tarefa-recorrente.model';

@Component({
  standalone: true,
  selector: 'jhi-contador-responsavel-tarefa-recorrente-detail',
  templateUrl: './contador-responsavel-tarefa-recorrente-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContadorResponsavelTarefaRecorrenteDetailComponent {
  contadorResponsavelTarefaRecorrente = input<IContadorResponsavelTarefaRecorrente | null>(null);

  previousState(): void {
    window.history.back();
  }
}
