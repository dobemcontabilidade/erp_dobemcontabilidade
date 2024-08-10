import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefa } from '../tarefa.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-detail',
  templateUrl: './tarefa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaDetailComponent {
  tarefa = input<ITarefa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
