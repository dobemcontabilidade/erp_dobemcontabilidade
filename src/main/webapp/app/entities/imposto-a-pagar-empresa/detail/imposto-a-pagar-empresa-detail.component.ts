import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-imposto-a-pagar-empresa-detail',
  templateUrl: './imposto-a-pagar-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ImpostoAPagarEmpresaDetailComponent {
  impostoAPagarEmpresa = input<IImpostoAPagarEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
