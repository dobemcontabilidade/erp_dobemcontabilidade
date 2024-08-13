import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';

@Component({
  standalone: true,
  selector: 'jhi-perfil-acesso-usuario-detail',
  templateUrl: './perfil-acesso-usuario-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PerfilAcessoUsuarioDetailComponent {
  perfilAcessoUsuario = input<IPerfilAcessoUsuario | null>(null);

  previousState(): void {
    window.history.back();
  }
}
