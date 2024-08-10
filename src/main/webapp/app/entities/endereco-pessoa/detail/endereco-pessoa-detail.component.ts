import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEnderecoPessoa } from '../endereco-pessoa.model';

@Component({
  standalone: true,
  selector: 'jhi-endereco-pessoa-detail',
  templateUrl: './endereco-pessoa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EnderecoPessoaDetailComponent {
  enderecoPessoa = input<IEnderecoPessoa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
