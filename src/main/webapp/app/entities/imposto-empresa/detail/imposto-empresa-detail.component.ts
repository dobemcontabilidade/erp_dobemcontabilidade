import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IImpostoEmpresa } from '../imposto-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-imposto-empresa-detail',
  templateUrl: './imposto-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ImpostoEmpresaDetailComponent {
  impostoEmpresa = input<IImpostoEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
