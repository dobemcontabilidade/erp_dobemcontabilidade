import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFluxoModelo } from '../fluxo-modelo.model';

@Component({
  standalone: true,
  selector: 'jhi-fluxo-modelo-detail',
  templateUrl: './fluxo-modelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FluxoModeloDetailComponent {
  fluxoModelo = input<IFluxoModelo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
