import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITermoContratoContabil } from '../termo-contrato-contabil.model';

@Component({
  standalone: true,
  selector: 'jhi-termo-contrato-contabil-detail',
  templateUrl: './termo-contrato-contabil-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TermoContratoContabilDetailComponent {
  termoContratoContabil = input<ITermoContratoContabil | null>(null);

  previousState(): void {
    window.history.back();
  }
}
