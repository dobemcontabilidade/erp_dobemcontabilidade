import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-usuario-empresa-detail',
  templateUrl: './grupo-acesso-usuario-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GrupoAcessoUsuarioEmpresaDetailComponent {
  grupoAcessoUsuarioEmpresa = input<IGrupoAcessoUsuarioEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
