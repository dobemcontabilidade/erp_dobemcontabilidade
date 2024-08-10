import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPerfilContador } from '../perfil-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-perfil-contador-detail',
  templateUrl: './perfil-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PerfilContadorDetailComponent {
  perfilContador = input<IPerfilContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
