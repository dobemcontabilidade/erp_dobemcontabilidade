import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAdicionalRamo } from '../adicional-ramo.model';

@Component({
  standalone: true,
  selector: 'jhi-adicional-ramo-detail',
  templateUrl: './adicional-ramo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AdicionalRamoDetailComponent {
  adicionalRamo = input<IAdicionalRamo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
