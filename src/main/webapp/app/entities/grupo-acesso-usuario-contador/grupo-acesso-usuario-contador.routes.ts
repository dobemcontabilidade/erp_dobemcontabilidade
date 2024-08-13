import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GrupoAcessoUsuarioContadorComponent } from './list/grupo-acesso-usuario-contador.component';
import { GrupoAcessoUsuarioContadorDetailComponent } from './detail/grupo-acesso-usuario-contador-detail.component';
import { GrupoAcessoUsuarioContadorUpdateComponent } from './update/grupo-acesso-usuario-contador-update.component';
import GrupoAcessoUsuarioContadorResolve from './route/grupo-acesso-usuario-contador-routing-resolve.service';

const grupoAcessoUsuarioContadorRoute: Routes = [
  {
    path: '',
    component: GrupoAcessoUsuarioContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrupoAcessoUsuarioContadorDetailComponent,
    resolve: {
      grupoAcessoUsuarioContador: GrupoAcessoUsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrupoAcessoUsuarioContadorUpdateComponent,
    resolve: {
      grupoAcessoUsuarioContador: GrupoAcessoUsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrupoAcessoUsuarioContadorUpdateComponent,
    resolve: {
      grupoAcessoUsuarioContador: GrupoAcessoUsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default grupoAcessoUsuarioContadorRoute;
