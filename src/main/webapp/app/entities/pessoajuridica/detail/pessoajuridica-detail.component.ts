import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPessoajuridica } from '../pessoajuridica.model';

@Component({
  standalone: true,
  selector: 'jhi-pessoajuridica-detail',
  templateUrl: './pessoajuridica-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PessoajuridicaDetailComponent {
  pessoajuridica = input<IPessoajuridica | null>(null);

  previousState(): void {
    window.history.back();
  }
}
