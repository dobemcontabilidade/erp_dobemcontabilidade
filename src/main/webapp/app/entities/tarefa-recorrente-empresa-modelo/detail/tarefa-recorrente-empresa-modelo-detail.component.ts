import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-recorrente-empresa-modelo-detail',
  templateUrl: './tarefa-recorrente-empresa-modelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaRecorrenteEmpresaModeloDetailComponent {
  tarefaRecorrenteEmpresaModelo = input<ITarefaRecorrenteEmpresaModelo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
