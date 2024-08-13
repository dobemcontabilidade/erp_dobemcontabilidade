import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGrupoAcessoEmpresa } from '../grupo-acesso-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-empresa-detail',
  templateUrl: './grupo-acesso-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GrupoAcessoEmpresaDetailComponent {
  grupoAcessoEmpresa = input<IGrupoAcessoEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
