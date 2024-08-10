import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAtividadeEmpresa } from '../atividade-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-atividade-empresa-detail',
  templateUrl: './atividade-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AtividadeEmpresaDetailComponent {
  atividadeEmpresa = input<IAtividadeEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
