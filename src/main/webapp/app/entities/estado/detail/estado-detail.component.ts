import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEstado } from '../estado.model';

@Component({
  standalone: true,
  selector: 'jhi-estado-detail',
  templateUrl: './estado-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EstadoDetailComponent {
  estado = input<IEstado | null>(null);

  previousState(): void {
    window.history.back();
  }
}
