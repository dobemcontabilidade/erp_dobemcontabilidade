import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';

@Component({
  standalone: true,
  selector: 'jhi-anexo-requerido-pessoa-detail',
  templateUrl: './anexo-requerido-pessoa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AnexoRequeridoPessoaDetailComponent {
  anexoRequeridoPessoa = input<IAnexoRequeridoPessoa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
