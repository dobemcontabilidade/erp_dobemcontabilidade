import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAssinaturaEmpresa } from '../assinatura-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-assinatura-empresa-detail',
  templateUrl: './assinatura-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AssinaturaEmpresaDetailComponent {
  assinaturaEmpresa = input<IAssinaturaEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
