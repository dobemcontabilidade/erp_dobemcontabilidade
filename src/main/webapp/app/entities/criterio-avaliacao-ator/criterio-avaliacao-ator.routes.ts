import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CriterioAvaliacaoAtorComponent } from './list/criterio-avaliacao-ator.component';
import { CriterioAvaliacaoAtorDetailComponent } from './detail/criterio-avaliacao-ator-detail.component';
import { CriterioAvaliacaoAtorUpdateComponent } from './update/criterio-avaliacao-ator-update.component';
import CriterioAvaliacaoAtorResolve from './route/criterio-avaliacao-ator-routing-resolve.service';

const criterioAvaliacaoAtorRoute: Routes = [
  {
    path: '',
    component: CriterioAvaliacaoAtorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CriterioAvaliacaoAtorDetailComponent,
    resolve: {
      criterioAvaliacaoAtor: CriterioAvaliacaoAtorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CriterioAvaliacaoAtorUpdateComponent,
    resolve: {
      criterioAvaliacaoAtor: CriterioAvaliacaoAtorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CriterioAvaliacaoAtorUpdateComponent,
    resolve: {
      criterioAvaliacaoAtor: CriterioAvaliacaoAtorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default criterioAvaliacaoAtorRoute;
