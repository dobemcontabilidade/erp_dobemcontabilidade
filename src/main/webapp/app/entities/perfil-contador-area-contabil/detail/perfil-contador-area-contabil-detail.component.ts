import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';

@Component({
  standalone: true,
  selector: 'jhi-perfil-contador-area-contabil-detail',
  templateUrl: './perfil-contador-area-contabil-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PerfilContadorAreaContabilDetailComponent {
  perfilContadorAreaContabil = input<IPerfilContadorAreaContabil | null>(null);

  previousState(): void {
    window.history.back();
  }
}
