import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-gateway-assinatura-empresa-detail',
  templateUrl: './gateway-assinatura-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GatewayAssinaturaEmpresaDetailComponent {
  gatewayAssinaturaEmpresa = input<IGatewayAssinaturaEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
