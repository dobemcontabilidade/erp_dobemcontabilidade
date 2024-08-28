import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEnderecoEmpresa } from '../endereco-empresa.model';

@Component({
  standalone: true,
  selector: 'jhi-endereco-empresa-detail',
  templateUrl: './endereco-empresa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EnderecoEmpresaDetailComponent {
  enderecoEmpresa = input<IEnderecoEmpresa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
