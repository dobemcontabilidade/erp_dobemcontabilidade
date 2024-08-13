import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-etapa-fluxo-execucao-detail',
  templateUrl: './etapa-fluxo-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EtapaFluxoExecucaoDetailComponent {
  etapaFluxoExecucao = input<IEtapaFluxoExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
