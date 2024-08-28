import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContadorComponent } from './list/contador.component';
import { ContadorDetailComponent } from './detail/contador-detail.component';
import { ContadorUpdateComponent } from './update/contador-update.component';
import ContadorResolve from './route/contador-routing-resolve.service';

const contadorRoute: Routes = [
  {
    path: '',
    component: ContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContadorDetailComponent,
    resolve: {
      contador: ContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContadorUpdateComponent,
    resolve: {
      contador: ContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContadorUpdateComponent,
    resolve: {
      contador: ContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contadorRoute;
