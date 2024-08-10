import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AdministradorComponent } from './list/administrador.component';
import { AdministradorDetailComponent } from './detail/administrador-detail.component';
import { AdministradorUpdateComponent } from './update/administrador-update.component';
import AdministradorResolve from './route/administrador-routing-resolve.service';

const administradorRoute: Routes = [
  {
    path: '',
    component: AdministradorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdministradorDetailComponent,
    resolve: {
      administrador: AdministradorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdministradorUpdateComponent,
    resolve: {
      administrador: AdministradorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdministradorUpdateComponent,
    resolve: {
      administrador: AdministradorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default administradorRoute;
