import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';

@Component({
  standalone: true,
  selector: 'jhi-calculo-plano-assinatura-detail',
  templateUrl: './calculo-plano-assinatura-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CalculoPlanoAssinaturaDetailComponent {
  calculoPlanoAssinatura = input<ICalculoPlanoAssinatura | null>(null);

  previousState(): void {
    window.history.back();
  }
}
