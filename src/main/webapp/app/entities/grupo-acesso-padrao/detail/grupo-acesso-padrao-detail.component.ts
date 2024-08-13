import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-padrao-detail',
  templateUrl: './grupo-acesso-padrao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GrupoAcessoPadraoDetailComponent {
  grupoAcessoPadrao = input<IGrupoAcessoPadrao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
