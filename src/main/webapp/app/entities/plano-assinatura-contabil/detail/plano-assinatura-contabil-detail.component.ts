import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';

@Component({
  standalone: true,
  selector: 'jhi-plano-assinatura-contabil-detail',
  templateUrl: './plano-assinatura-contabil-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PlanoAssinaturaContabilDetailComponent {
  planoAssinaturaContabil = input<IPlanoAssinaturaContabil | null>(null);

  previousState(): void {
    window.history.back();
  }
}
