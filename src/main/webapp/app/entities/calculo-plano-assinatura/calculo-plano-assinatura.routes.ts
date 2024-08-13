import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CalculoPlanoAssinaturaComponent } from './list/calculo-plano-assinatura.component';
import { CalculoPlanoAssinaturaDetailComponent } from './detail/calculo-plano-assinatura-detail.component';
import { CalculoPlanoAssinaturaUpdateComponent } from './update/calculo-plano-assinatura-update.component';
import CalculoPlanoAssinaturaResolve from './route/calculo-plano-assinatura-routing-resolve.service';

const calculoPlanoAssinaturaRoute: Routes = [
  {
    path: '',
    component: CalculoPlanoAssinaturaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CalculoPlanoAssinaturaDetailComponent,
    resolve: {
      calculoPlanoAssinatura: CalculoPlanoAssinaturaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CalculoPlanoAssinaturaUpdateComponent,
    resolve: {
      calculoPlanoAssinatura: CalculoPlanoAssinaturaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CalculoPlanoAssinaturaUpdateComponent,
    resolve: {
      calculoPlanoAssinatura: CalculoPlanoAssinaturaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default calculoPlanoAssinaturaRoute;
