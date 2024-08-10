import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDepartamentoContador } from '../departamento-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-departamento-contador-detail',
  templateUrl: './departamento-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DepartamentoContadorDetailComponent {
  departamentoContador = input<IDepartamentoContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
