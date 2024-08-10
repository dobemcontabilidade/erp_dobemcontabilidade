import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPlanoContabil } from '../plano-contabil.model';

@Component({
  standalone: true,
  selector: 'jhi-plano-contabil-detail',
  templateUrl: './plano-contabil-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PlanoContabilDetailComponent {
  planoContabil = input<IPlanoContabil | null>(null);

  previousState(): void {
    window.history.back();
  }
}
