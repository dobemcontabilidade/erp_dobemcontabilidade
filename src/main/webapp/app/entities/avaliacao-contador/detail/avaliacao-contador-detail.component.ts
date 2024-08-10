import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAvaliacaoContador } from '../avaliacao-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-avaliacao-contador-detail',
  templateUrl: './avaliacao-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AvaliacaoContadorDetailComponent {
  avaliacaoContador = input<IAvaliacaoContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
