import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PlanoContaAzulComponent } from './list/plano-conta-azul.component';
import { PlanoContaAzulDetailComponent } from './detail/plano-conta-azul-detail.component';
import { PlanoContaAzulUpdateComponent } from './update/plano-conta-azul-update.component';
import PlanoContaAzulResolve from './route/plano-conta-azul-routing-resolve.service';

const planoContaAzulRoute: Routes = [
  {
    path: '',
    component: PlanoContaAzulComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoContaAzulDetailComponent,
    resolve: {
      planoContaAzul: PlanoContaAzulResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoContaAzulUpdateComponent,
    resolve: {
      planoContaAzul: PlanoContaAzulResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoContaAzulUpdateComponent,
    resolve: {
      planoContaAzul: PlanoContaAzulResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default planoContaAzulRoute;
