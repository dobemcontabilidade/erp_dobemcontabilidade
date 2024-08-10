import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDepartamentoFuncionario } from '../departamento-funcionario.model';

@Component({
  standalone: true,
  selector: 'jhi-departamento-funcionario-detail',
  templateUrl: './departamento-funcionario-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DepartamentoFuncionarioDetailComponent {
  departamentoFuncionario = input<IDepartamentoFuncionario | null>(null);

  previousState(): void {
    window.history.back();
  }
}
