import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IValorBaseRamo } from '../valor-base-ramo.model';

@Component({
  standalone: true,
  selector: 'jhi-valor-base-ramo-detail',
  templateUrl: './valor-base-ramo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ValorBaseRamoDetailComponent {
  valorBaseRamo = input<IValorBaseRamo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
