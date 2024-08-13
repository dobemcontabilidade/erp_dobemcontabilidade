import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';

@Component({
  standalone: true,
  selector: 'jhi-criterio-avaliacao-ator-detail',
  templateUrl: './criterio-avaliacao-ator-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CriterioAvaliacaoAtorDetailComponent {
  criterioAvaliacaoAtor = input<ICriterioAvaliacaoAtor | null>(null);

  previousState(): void {
    window.history.back();
  }
}
