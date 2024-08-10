import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IBanco } from '../banco.model';

@Component({
  standalone: true,
  selector: 'jhi-banco-detail',
  templateUrl: './banco-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class BancoDetailComponent {
  banco = input<IBanco | null>(null);

  previousState(): void {
    window.history.back();
  }
}
