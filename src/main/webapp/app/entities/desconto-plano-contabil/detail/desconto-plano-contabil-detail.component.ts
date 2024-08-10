import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDescontoPlanoContabil } from '../desconto-plano-contabil.model';

@Component({
  standalone: true,
  selector: 'jhi-desconto-plano-contabil-detail',
  templateUrl: './desconto-plano-contabil-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DescontoPlanoContabilDetailComponent {
  descontoPlanoContabil = input<IDescontoPlanoContabil | null>(null);

  previousState(): void {
    window.history.back();
  }
}
