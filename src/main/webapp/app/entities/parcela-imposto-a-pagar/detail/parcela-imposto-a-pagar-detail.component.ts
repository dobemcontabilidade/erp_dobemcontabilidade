import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IParcelaImpostoAPagar } from '../parcela-imposto-a-pagar.model';

@Component({
  standalone: true,
  selector: 'jhi-parcela-imposto-a-pagar-detail',
  templateUrl: './parcela-imposto-a-pagar-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ParcelaImpostoAPagarDetailComponent {
  parcelaImpostoAPagar = input<IParcelaImpostoAPagar | null>(null);

  previousState(): void {
    window.history.back();
  }
}
