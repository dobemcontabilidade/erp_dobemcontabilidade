import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IImpostoParcelado } from '../imposto-parcelado.model';

@Component({
  standalone: true,
  selector: 'jhi-imposto-parcelado-detail',
  templateUrl: './imposto-parcelado-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ImpostoParceladoDetailComponent {
  impostoParcelado = input<IImpostoParcelado | null>(null);

  previousState(): void {
    window.history.back();
  }
}
