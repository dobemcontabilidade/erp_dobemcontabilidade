import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEmpresaVinculada } from '../empresa-vinculada.model';

@Component({
  standalone: true,
  selector: 'jhi-empresa-vinculada-detail',
  templateUrl: './empresa-vinculada-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EmpresaVinculadaDetailComponent {
  empresaVinculada = input<IEmpresaVinculada | null>(null);

  previousState(): void {
    window.history.back();
  }
}
