import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PerfilContadorComponent } from './list/perfil-contador.component';
import { PerfilContadorDetailComponent } from './detail/perfil-contador-detail.component';
import { PerfilContadorUpdateComponent } from './update/perfil-contador-update.component';
import PerfilContadorResolve from './route/perfil-contador-routing-resolve.service';

const perfilContadorRoute: Routes = [
  {
    path: '',
    component: PerfilContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfilContadorDetailComponent,
    resolve: {
      perfilContador: PerfilContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfilContadorUpdateComponent,
    resolve: {
      perfilContador: PerfilContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfilContadorUpdateComponent,
    resolve: {
      perfilContador: PerfilContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default perfilContadorRoute;
