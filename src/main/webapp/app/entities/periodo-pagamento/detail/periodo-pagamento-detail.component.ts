import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPeriodoPagamento } from '../periodo-pagamento.model';

@Component({
  standalone: true,
  selector: 'jhi-periodo-pagamento-detail',
  templateUrl: './periodo-pagamento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PeriodoPagamentoDetailComponent {
  periodoPagamento = input<IPeriodoPagamento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
