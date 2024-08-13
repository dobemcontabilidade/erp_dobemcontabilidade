import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-ordem-servico-detail',
  templateUrl: './servico-contabil-ordem-servico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ServicoContabilOrdemServicoDetailComponent {
  servicoContabilOrdemServico = input<IServicoContabilOrdemServico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
