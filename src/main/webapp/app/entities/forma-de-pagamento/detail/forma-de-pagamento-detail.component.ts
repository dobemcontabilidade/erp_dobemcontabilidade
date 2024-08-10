import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFormaDePagamento } from '../forma-de-pagamento.model';

@Component({
  standalone: true,
  selector: 'jhi-forma-de-pagamento-detail',
  templateUrl: './forma-de-pagamento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FormaDePagamentoDetailComponent {
  formaDePagamento = input<IFormaDePagamento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
