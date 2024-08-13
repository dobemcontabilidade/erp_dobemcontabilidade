import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ISubTarefaRecorrente } from '../sub-tarefa-recorrente.model';

@Component({
  standalone: true,
  selector: 'jhi-sub-tarefa-recorrente-detail',
  templateUrl: './sub-tarefa-recorrente-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SubTarefaRecorrenteDetailComponent {
  subTarefaRecorrente = input<ISubTarefaRecorrente | null>(null);

  previousState(): void {
    window.history.back();
  }
}
