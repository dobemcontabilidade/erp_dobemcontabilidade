import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';

@Component({
  standalone: true,
  selector: 'jhi-imposto-empresa-modelo-detail',
  templateUrl: './imposto-empresa-modelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ImpostoEmpresaModeloDetailComponent {
  impostoEmpresaModelo = input<IImpostoEmpresaModelo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
