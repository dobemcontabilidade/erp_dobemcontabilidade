import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-etapa-fluxo-execucao-detail',
  templateUrl: './servico-contabil-etapa-fluxo-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ServicoContabilEtapaFluxoExecucaoDetailComponent {
  servicoContabilEtapaFluxoExecucao = input<IServicoContabilEtapaFluxoExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
