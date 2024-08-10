import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAreaContabilContador } from '../area-contabil-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-contador-detail',
  templateUrl: './area-contabil-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AreaContabilContadorDetailComponent {
  areaContabilContador = input<IAreaContabilContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
