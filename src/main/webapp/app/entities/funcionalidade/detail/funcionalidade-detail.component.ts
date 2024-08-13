import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFuncionalidade } from '../funcionalidade.model';

@Component({
  standalone: true,
  selector: 'jhi-funcionalidade-detail',
  templateUrl: './funcionalidade-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FuncionalidadeDetailComponent {
  funcionalidade = input<IFuncionalidade | null>(null);

  previousState(): void {
    window.history.back();
  }
}
