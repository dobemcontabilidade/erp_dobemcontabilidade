import { Component, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { DataUtils } from 'app/core/util/data-util.service';
import { ITermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-termo-contrato-assinatura-empresa-detail',
  templateUrl: './termo-contrato-assinatura-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TermoContratoAssinaturaEmpresaDetailComponent {
  termoContratoAssinaturaEmpresa = input<ITermoContratoAssinaturaEmpresa | null>(null);

  protected dataUtils = inject(DataUtils);

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
