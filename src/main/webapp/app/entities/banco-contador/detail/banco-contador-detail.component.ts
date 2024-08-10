import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IBancoContador } from '../banco-contador.model';

@Component({
  standalone: true,
  selector: 'jhi-banco-contador-detail',
  templateUrl: './banco-contador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class BancoContadorDetailComponent {
  bancoContador = input<IBancoContador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
