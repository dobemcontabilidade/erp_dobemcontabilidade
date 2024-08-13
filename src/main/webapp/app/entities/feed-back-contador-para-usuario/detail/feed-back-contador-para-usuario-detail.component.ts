import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';

@Component({
  standalone: true,
  selector: 'jhi-feed-back-contador-para-usuario-detail',
  templateUrl: './feed-back-contador-para-usuario-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FeedBackContadorParaUsuarioDetailComponent {
  feedBackContadorParaUsuario = input<IFeedBackContadorParaUsuario | null>(null);

  previousState(): void {
    window.history.back();
  }
}
