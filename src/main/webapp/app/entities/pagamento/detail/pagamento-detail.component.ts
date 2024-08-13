import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPagamento } from '../pagamento.model';

@Component({
  standalone: true,
  selector: 'jhi-pagamento-detail',
  templateUrl: './pagamento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PagamentoDetailComponent {
  pagamento = input<IPagamento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
