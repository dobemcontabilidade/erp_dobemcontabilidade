import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';

@Component({
  standalone: true,
  selector: 'jhi-funcionalidade-grupo-acesso-padrao-detail',
  templateUrl: './funcionalidade-grupo-acesso-padrao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FuncionalidadeGrupoAcessoPadraoDetailComponent {
  funcionalidadeGrupoAcessoPadrao = input<IFuncionalidadeGrupoAcessoPadrao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
