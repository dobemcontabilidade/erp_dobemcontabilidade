import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-etapa-fluxo-modelo-detail',
  templateUrl: './servico-contabil-etapa-fluxo-modelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ServicoContabilEtapaFluxoModeloDetailComponent {
  servicoContabilEtapaFluxoModelo = input<IServicoContabilEtapaFluxoModelo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
