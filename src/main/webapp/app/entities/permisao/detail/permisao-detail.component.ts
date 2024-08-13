import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPermisao } from '../permisao.model';

@Component({
  standalone: true,
  selector: 'jhi-permisao-detail',
  templateUrl: './permisao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PermisaoDetailComponent {
  permisao = input<IPermisao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
