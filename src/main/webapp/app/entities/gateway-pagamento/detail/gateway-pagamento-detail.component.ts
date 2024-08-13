import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGatewayPagamento } from '../gateway-pagamento.model';

@Component({
  standalone: true,
  selector: 'jhi-gateway-pagamento-detail',
  templateUrl: './gateway-pagamento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GatewayPagamentoDetailComponent {
  gatewayPagamento = input<IGatewayPagamento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
