import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICobrancaEmpresa } from '../cobranca-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-cobranca-empresa-detail',
  templateUrl: './cobranca-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CobrancaEmpresaDetailComponent {
  cobrancaEmpresa = input<ICobrancaEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
