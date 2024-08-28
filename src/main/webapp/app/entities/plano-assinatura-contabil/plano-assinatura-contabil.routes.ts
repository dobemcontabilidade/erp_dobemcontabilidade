import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PlanoAssinaturaContabilComponent } from './list/plano-assinatura-contabil.component';
import { PlanoAssinaturaContabilDetailComponent } from './detail/plano-assinatura-contabil-detail.component';
import { PlanoAssinaturaContabilUpdateComponent } from './update/plano-assinatura-contabil-update.component';
import PlanoAssinaturaContabilResolve from './route/plano-assinatura-contabil-routing-resolve.service';

const planoAssinaturaContabilRoute: Routes = [
  {
    path: '',
    component: PlanoAssinaturaContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoAssinaturaContabilDetailComponent,
    resolve: {
      planoAssinaturaContabil: PlanoAssinaturaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoAssinaturaContabilUpdateComponent,
    resolve: {
      planoAssinaturaContabil: PlanoAssinaturaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoAssinaturaContabilUpdateComponent,
    resolve: {
      planoAssinaturaContabil: PlanoAssinaturaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default planoAssinaturaContabilRoute;
