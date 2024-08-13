import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICriterioAvaliacao } from '../criterio-avaliacao.model';

@Component({
  standalone: true,
  selector: 'jhi-criterio-avaliacao-detail',
  templateUrl: './criterio-avaliacao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CriterioAvaliacaoDetailComponent {
  criterioAvaliacao = input<ICriterioAvaliacao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
