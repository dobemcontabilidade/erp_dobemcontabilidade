import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AvaliacaoContadorComponent } from './list/avaliacao-contador.component';
import { AvaliacaoContadorDetailComponent } from './detail/avaliacao-contador-detail.component';
import { AvaliacaoContadorUpdateComponent } from './update/avaliacao-contador-update.component';
import AvaliacaoContadorResolve from './route/avaliacao-contador-routing-resolve.service';

const avaliacaoContadorRoute: Routes = [
  {
    path: '',
    component: AvaliacaoContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvaliacaoContadorDetailComponent,
    resolve: {
      avaliacaoContador: AvaliacaoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvaliacaoContadorUpdateComponent,
    resolve: {
      avaliacaoContador: AvaliacaoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvaliacaoContadorUpdateComponent,
    resolve: {
      avaliacaoContador: AvaliacaoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default avaliacaoContadorRoute;
