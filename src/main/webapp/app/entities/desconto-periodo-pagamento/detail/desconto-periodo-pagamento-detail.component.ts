import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';

@Component({
  standalone: true,
  selector: 'jhi-desconto-periodo-pagamento-detail',
  templateUrl: './desconto-periodo-pagamento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DescontoPeriodoPagamentoDetailComponent {
  descontoPeriodoPagamento = input<IDescontoPeriodoPagamento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
