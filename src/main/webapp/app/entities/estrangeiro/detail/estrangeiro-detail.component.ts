import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEstrangeiro } from '../estrangeiro.model';

@Component({
  standalone: true,
  selector: 'jhi-estrangeiro-detail',
  templateUrl: './estrangeiro-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EstrangeiroDetailComponent {
  estrangeiro = input<IEstrangeiro | null>(null);

  previousState(): void {
    window.history.back();
  }
}
