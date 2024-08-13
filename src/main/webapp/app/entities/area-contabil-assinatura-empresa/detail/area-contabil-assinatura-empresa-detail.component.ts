import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-assinatura-empresa-detail',
  templateUrl: './area-contabil-assinatura-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AreaContabilAssinaturaEmpresaDetailComponent {
  areaContabilAssinaturaEmpresa = input<IAreaContabilAssinaturaEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
