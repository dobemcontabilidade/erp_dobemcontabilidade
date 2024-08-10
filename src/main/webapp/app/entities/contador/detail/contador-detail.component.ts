import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContador } from '../contador.model';

@Component({
  standalone: true,
  selector: 'jhi-contador-detail',
  templateUrl: './contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContadorDetailComponent {
  contador = input<IContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
