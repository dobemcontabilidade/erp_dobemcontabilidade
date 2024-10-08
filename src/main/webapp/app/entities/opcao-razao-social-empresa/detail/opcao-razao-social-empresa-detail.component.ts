import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-opcao-razao-social-empresa-detail',
  templateUrl: './opcao-razao-social-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class OpcaoRazaoSocialEmpresaDetailComponent {
  opcaoRazaoSocialEmpresa = input<IOpcaoRazaoSocialEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
