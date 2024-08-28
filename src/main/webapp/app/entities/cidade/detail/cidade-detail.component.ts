import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICidade } from '../cidade.model';

@Component({
  standalone: true,
  selector: 'jhi-cidade-detail',
  templateUrl: './cidade-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CidadeDetailComponent {
  cidade = input<ICidade | null>(null);

  previousState(): void {
    window.history.back();
  }
}
