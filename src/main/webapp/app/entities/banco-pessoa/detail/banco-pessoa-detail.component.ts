import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IBancoPessoa } from '../banco-pessoa.model';

@Component({
  standalone: true,
  selector: 'jhi-banco-pessoa-detail',
  templateUrl: './banco-pessoa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class BancoPessoaDetailComponent {
  bancoPessoa = input<IBancoPessoa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
