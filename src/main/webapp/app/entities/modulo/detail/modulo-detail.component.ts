import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IModulo } from '../modulo.model';

@Component({
  standalone: true,
  selector: 'jhi-modulo-detail',
  templateUrl: './modulo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ModuloDetailComponent {
  modulo = input<IModulo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
