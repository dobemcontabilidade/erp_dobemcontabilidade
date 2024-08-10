import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UsuarioContadorComponent } from './list/usuario-contador.component';
import { UsuarioContadorDetailComponent } from './detail/usuario-contador-detail.component';
import { UsuarioContadorUpdateComponent } from './update/usuario-contador-update.component';
import UsuarioContadorResolve from './route/usuario-contador-routing-resolve.service';

const usuarioContadorRoute: Routes = [
  {
    path: '',
    component: UsuarioContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsuarioContadorDetailComponent,
    resolve: {
      usuarioContador: UsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsuarioContadorUpdateComponent,
    resolve: {
      usuarioContador: UsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsuarioContadorUpdateComponent,
    resolve: {
      usuarioContador: UsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default usuarioContadorRoute;
