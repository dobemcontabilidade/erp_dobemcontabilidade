import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPais } from '../pais.model';

@Component({
  standalone: true,
  selector: 'jhi-pais-detail',
  templateUrl: './pais-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PaisDetailComponent {
  pais = input<IPais | null>(null);

  previousState(): void {
    window.history.back();
  }
}
