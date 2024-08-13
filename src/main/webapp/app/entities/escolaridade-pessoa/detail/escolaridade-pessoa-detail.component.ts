import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEscolaridadePessoa } from '../escolaridade-pessoa.model';

@Component({
  standalone: true,
  selector: 'jhi-escolaridade-pessoa-detail',
  templateUrl: './escolaridade-pessoa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EscolaridadePessoaDetailComponent {
  escolaridadePessoa = input<IEscolaridadePessoa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
