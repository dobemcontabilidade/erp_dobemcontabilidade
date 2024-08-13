import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAgendaContadorConfig } from '../agenda-contador-config.model';

@Component({
  standalone: true,
  selector: 'jhi-agenda-contador-config-detail',
  templateUrl: './agenda-contador-config-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AgendaContadorConfigDetailComponent {
  agendaContadorConfig = input<IAgendaContadorConfig | null>(null);

  previousState(): void {
    window.history.back();
  }
}
