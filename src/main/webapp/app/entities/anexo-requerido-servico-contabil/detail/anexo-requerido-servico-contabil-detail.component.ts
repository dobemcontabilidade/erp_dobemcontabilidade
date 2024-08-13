import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-servico-contabil-detail',
  templateUrl: './anexo-requerido-servico-contabil-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AnexoRequeridoServicoContabilDetailComponent {
  anexoRequeridoServicoContabil = input<IAnexoRequeridoServicoContabil | null>(null);

  previousState(): void {
    window.history.back();
  }
}
