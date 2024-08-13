import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAnexoServicoContabilEmpresa } from '../anexo-servico-contabil-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-anexo-servico-contabil-empresa-detail',
  templateUrl: './anexo-servico-contabil-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AnexoServicoContabilEmpresaDetailComponent {
  anexoServicoContabilEmpresa = input<IAnexoServicoContabilEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
