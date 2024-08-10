import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-empresa-detail',
  templateUrl: './area-contabil-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AreaContabilEmpresaDetailComponent {
  areaContabilEmpresa = input<IAreaContabilEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
