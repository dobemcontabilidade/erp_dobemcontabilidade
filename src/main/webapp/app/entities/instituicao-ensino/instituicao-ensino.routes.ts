import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { InstituicaoEnsinoComponent } from './list/instituicao-ensino.component';
import { InstituicaoEnsinoDetailComponent } from './detail/instituicao-ensino-detail.component';
import { InstituicaoEnsinoUpdateComponent } from './update/instituicao-ensino-update.component';
import InstituicaoEnsinoResolve from './route/instituicao-ensino-routing-resolve.service';

const instituicaoEnsinoRoute: Routes = [
  {
    path: '',
    component: InstituicaoEnsinoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InstituicaoEnsinoDetailComponent,
    resolve: {
      instituicaoEnsino: InstituicaoEnsinoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InstituicaoEnsinoUpdateComponent,
    resolve: {
      instituicaoEnsino: InstituicaoEnsinoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InstituicaoEnsinoUpdateComponent,
    resolve: {
      instituicaoEnsino: InstituicaoEnsinoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default instituicaoEnsinoRoute;
