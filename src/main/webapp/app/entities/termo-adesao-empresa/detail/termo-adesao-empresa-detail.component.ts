import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITermoAdesaoEmpresa } from '../termo-adesao-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-termo-adesao-empresa-detail',
  templateUrl: './termo-adesao-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TermoAdesaoEmpresaDetailComponent {
  termoAdesaoEmpresa = input<ITermoAdesaoEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
