import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';

@Component({
  standalone: true,
  selector: 'jhi-contador-responsavel-ordem-servico-detail',
  templateUrl: './contador-responsavel-ordem-servico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContadorResponsavelOrdemServicoDetailComponent {
  contadorResponsavelOrdemServico = input<IContadorResponsavelOrdemServico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
