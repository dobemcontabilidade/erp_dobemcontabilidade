import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-empresa-usuario-contador-detail',
  templateUrl: './grupo-acesso-empresa-usuario-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GrupoAcessoEmpresaUsuarioContadorDetailComponent {
  grupoAcessoEmpresaUsuarioContador = input<IGrupoAcessoEmpresaUsuarioContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
