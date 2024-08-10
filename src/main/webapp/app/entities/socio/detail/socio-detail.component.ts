import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ISocio } from '../socio.model';

@Component({
  standalone: true,
  selector: 'jhi-socio-detail',
  templateUrl: './socio-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SocioDetailComponent {
  socio = input<ISocio | null>(null);

  previousState(): void {
    window.history.back();
  }
}
