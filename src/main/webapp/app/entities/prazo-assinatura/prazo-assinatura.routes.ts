import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PrazoAssinaturaComponent } from './list/prazo-assinatura.component';
import { PrazoAssinaturaDetailComponent } from './detail/prazo-assinatura-detail.component';
import { PrazoAssinaturaUpdateComponent } from './update/prazo-assinatura-update.component';
import PrazoAssinaturaResolve from './route/prazo-assinatura-routing-resolve.service';

const prazoAssinaturaRoute: Routes = [
  {
    path: '',
    component: PrazoAssinaturaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrazoAssinaturaDetailComponent,
    resolve: {
      prazoAssinatura: PrazoAssinaturaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrazoAssinaturaUpdateComponent,
    resolve: {
      prazoAssinatura: PrazoAssinaturaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrazoAssinaturaUpdateComponent,
    resolve: {
      prazoAssinatura: PrazoAssinaturaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default prazoAssinaturaRoute;
