import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EscolaridadeComponent } from './list/escolaridade.component';
import { EscolaridadeDetailComponent } from './detail/escolaridade-detail.component';
import { EscolaridadeUpdateComponent } from './update/escolaridade-update.component';
import EscolaridadeResolve from './route/escolaridade-routing-resolve.service';

const escolaridadeRoute: Routes = [
  {
    path: '',
    component: EscolaridadeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EscolaridadeDetailComponent,
    resolve: {
      escolaridade: EscolaridadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EscolaridadeUpdateComponent,
    resolve: {
      escolaridade: EscolaridadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EscolaridadeUpdateComponent,
    resolve: {
      escolaridade: EscolaridadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default escolaridadeRoute;
