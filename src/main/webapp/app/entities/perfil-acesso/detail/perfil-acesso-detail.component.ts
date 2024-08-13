import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPerfilAcesso } from '../perfil-acesso.model';

@Component({
  standalone: true,
  selector: 'jhi-perfil-acesso-detail',
  templateUrl: './perfil-acesso-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PerfilAcessoDetailComponent {
  perfilAcesso = input<IPerfilAcesso | null>(null);

  previousState(): void {
    window.history.back();
  }
}
