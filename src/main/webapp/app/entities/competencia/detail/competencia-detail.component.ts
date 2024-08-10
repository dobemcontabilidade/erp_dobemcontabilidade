import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICompetencia } from '../competencia.model';

@Component({
  standalone: true,
  selector: 'jhi-competencia-detail',
  templateUrl: './competencia-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CompetenciaDetailComponent {
  competencia = input<ICompetencia | null>(null);

  previousState(): void {
    window.history.back();
  }
}
