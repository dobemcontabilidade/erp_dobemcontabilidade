import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITarefaEmpresa } from '../tarefa-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-empresa-detail',
  templateUrl: './tarefa-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TarefaEmpresaDetailComponent {
  tarefaEmpresa = input<ITarefaEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
