import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDemissaoFuncionario } from '../demissao-funcionario.model';

@Component({
  standalone: true,
  selector: 'jhi-demissao-funcionario-detail',
  templateUrl: './demissao-funcionario-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DemissaoFuncionarioDetailComponent {
  demissaoFuncionario = input<IDemissaoFuncionario | null>(null);

  previousState(): void {
    window.history.back();
  }
}
