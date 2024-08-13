import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-funcionalidade-grupo-acesso-empresa-detail',
  templateUrl: './funcionalidade-grupo-acesso-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FuncionalidadeGrupoAcessoEmpresaDetailComponent {
  funcionalidadeGrupoAcessoEmpresa = input<IFuncionalidadeGrupoAcessoEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
