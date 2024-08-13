import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-usuario-contador-detail',
  templateUrl: './grupo-acesso-usuario-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GrupoAcessoUsuarioContadorDetailComponent {
  grupoAcessoUsuarioContador = input<IGrupoAcessoUsuarioContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
