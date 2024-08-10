import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITermoAdesaoContador } from '../termo-adesao-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-termo-adesao-contador-detail',
  templateUrl: './termo-adesao-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TermoAdesaoContadorDetailComponent {
  termoAdesaoContador = input<ITermoAdesaoContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
