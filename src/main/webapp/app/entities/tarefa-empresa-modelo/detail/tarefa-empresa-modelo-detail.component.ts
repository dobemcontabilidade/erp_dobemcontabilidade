import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-empresa-modelo-detail',
  templateUrl: './tarefa-empresa-modelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaEmpresaModeloDetailComponent {
  tarefaEmpresaModelo = input<ITarefaEmpresaModelo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
