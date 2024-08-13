import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEmpresaModelo } from '../empresa-modelo.model';

@Component({
  standalone: true,
  selector: 'jhi-empresa-modelo-detail',
  templateUrl: './empresa-modelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EmpresaModeloDetailComponent {
  empresaModelo = input<IEmpresaModelo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
