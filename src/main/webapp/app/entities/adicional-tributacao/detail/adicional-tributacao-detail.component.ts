import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAdicionalTributacao } from '../adicional-tributacao.model';

@Component({
  standalone: true,
  selector: 'jhi-adicional-tributacao-detail',
  templateUrl: './adicional-tributacao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AdicionalTributacaoDetailComponent {
  adicionalTributacao = input<IAdicionalTributacao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
