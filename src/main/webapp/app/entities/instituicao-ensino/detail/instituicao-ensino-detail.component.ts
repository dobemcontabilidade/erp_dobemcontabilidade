import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IInstituicaoEnsino } from '../instituicao-ensino.model';

@Component({
  standalone: true,
  selector: 'jhi-instituicao-ensino-detail',
  templateUrl: './instituicao-ensino-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class InstituicaoEnsinoDetailComponent {
  instituicaoEnsino = input<IInstituicaoEnsino | null>(null);

  previousState(): void {
    window.history.back();
  }
}
