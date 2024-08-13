import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IOrdemServico } from '../ordem-servico.model';

@Component({
  standalone: true,
  selector: 'jhi-ordem-servico-detail',
  templateUrl: './ordem-servico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class OrdemServicoDetailComponent {
  ordemServico = input<IOrdemServico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
