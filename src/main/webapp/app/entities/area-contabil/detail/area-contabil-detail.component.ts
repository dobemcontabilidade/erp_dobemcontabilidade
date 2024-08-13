import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAreaContabil } from '../area-contabil.model';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-detail',
  templateUrl: './area-contabil-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AreaContabilDetailComponent {
  areaContabil = input<IAreaContabil | null>(null);

  previousState(): void {
    window.history.back();
  }
}
