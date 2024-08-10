import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UsuarioGestaoComponent } from './list/usuario-gestao.component';
import { UsuarioGestaoDetailComponent } from './detail/usuario-gestao-detail.component';
import { UsuarioGestaoUpdateComponent } from './update/usuario-gestao-update.component';
import UsuarioGestaoResolve from './route/usuario-gestao-routing-resolve.service';

const usuarioGestaoRoute: Routes = [
  {
    path: '',
    component: UsuarioGestaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsuarioGestaoDetailComponent,
    resolve: {
      usuarioGestao: UsuarioGestaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsuarioGestaoUpdateComponent,
    resolve: {
      usuarioGestao: UsuarioGestaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsuarioGestaoUpdateComponent,
    resolve: {
      usuarioGestao: UsuarioGestaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default usuarioGestaoRoute;
