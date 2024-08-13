import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';

@Component({
  standalone: true,
  selector: 'jhi-servico-contabil-empresa-modelo-detail',
  templateUrl: './servico-contabil-empresa-modelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ServicoContabilEmpresaModeloDetailComponent {
  servicoContabilEmpresaModelo = input<IServicoContabilEmpresaModelo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
