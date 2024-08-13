import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAtorAvaliado } from '../ator-avaliado.model';

@Component({
  standalone: true,
  selector: 'jhi-ator-avaliado-detail',
  templateUrl: './ator-avaliado-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AtorAvaliadoDetailComponent {
  atorAvaliado = input<IAtorAvaliado | null>(null);

  previousState(): void {
    window.history.back();
  }
}
