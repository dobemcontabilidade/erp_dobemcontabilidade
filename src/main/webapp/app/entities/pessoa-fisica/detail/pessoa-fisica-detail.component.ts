import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPessoaFisica } from '../pessoa-fisica.model';

@Component({
  standalone: true,
  selector: 'jhi-pessoa-fisica-detail',
  templateUrl: './pessoa-fisica-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PessoaFisicaDetailComponent {
  pessoaFisica = input<IPessoaFisica | null>(null);

  previousState(): void {
    window.history.back();
  }
}
