import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CriterioAvaliacaoComponent } from './list/criterio-avaliacao.component';
import { CriterioAvaliacaoDetailComponent } from './detail/criterio-avaliacao-detail.component';
import { CriterioAvaliacaoUpdateComponent } from './update/criterio-avaliacao-update.component';
import CriterioAvaliacaoResolve from './route/criterio-avaliacao-routing-resolve.service';

const criterioAvaliacaoRoute: Routes = [
  {
    path: '',
    component: CriterioAvaliacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CriterioAvaliacaoDetailComponent,
    resolve: {
      criterioAvaliacao: CriterioAvaliacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CriterioAvaliacaoUpdateComponent,
    resolve: {
      criterioAvaliacao: CriterioAvaliacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CriterioAvaliacaoUpdateComponent,
    resolve: {
      criterioAvaliacao: CriterioAvaliacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default criterioAvaliacaoRoute;
