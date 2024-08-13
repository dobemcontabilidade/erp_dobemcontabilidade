import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';

@Component({
  standalone: true,
  selector: 'jhi-anexo-ordem-servico-execucao-detail',
  templateUrl: './anexo-ordem-servico-execucao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AnexoOrdemServicoExecucaoDetailComponent {
  anexoOrdemServicoExecucao = input<IAnexoOrdemServicoExecucao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
