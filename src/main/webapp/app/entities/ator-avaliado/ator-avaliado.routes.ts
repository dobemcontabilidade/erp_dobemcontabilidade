import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AtorAvaliadoComponent } from './list/ator-avaliado.component';
import { AtorAvaliadoDetailComponent } from './detail/ator-avaliado-detail.component';
import { AtorAvaliadoUpdateComponent } from './update/ator-avaliado-update.component';
import AtorAvaliadoResolve from './route/ator-avaliado-routing-resolve.service';

const atorAvaliadoRoute: Routes = [
  {
    path: '',
    component: AtorAvaliadoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AtorAvaliadoDetailComponent,
    resolve: {
      atorAvaliado: AtorAvaliadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AtorAvaliadoUpdateComponent,
    resolve: {
      atorAvaliado: AtorAvaliadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AtorAvaliadoUpdateComponent,
    resolve: {
      atorAvaliado: AtorAvaliadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default atorAvaliadoRoute;
