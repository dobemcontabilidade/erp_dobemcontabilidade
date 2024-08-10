import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAdicionalEnquadramento } from '../adicional-enquadramento.model';

@Component({
  standalone: true,
  selector: 'jhi-adicional-enquadramento-detail',
  templateUrl: './adicional-enquadramento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AdicionalEnquadramentoDetailComponent {
  adicionalEnquadramento = input<IAdicionalEnquadramento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
