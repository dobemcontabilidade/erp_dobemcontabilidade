import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ModuloComponent } from './list/modulo.component';
import { ModuloDetailComponent } from './detail/modulo-detail.component';
import { ModuloUpdateComponent } from './update/modulo-update.component';
import ModuloResolve from './route/modulo-routing-resolve.service';

const moduloRoute: Routes = [
  {
    path: '',
    component: ModuloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModuloDetailComponent,
    resolve: {
      modulo: ModuloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModuloUpdateComponent,
    resolve: {
      modulo: ModuloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModuloUpdateComponent,
    resolve: {
      modulo: ModuloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default moduloRoute;
