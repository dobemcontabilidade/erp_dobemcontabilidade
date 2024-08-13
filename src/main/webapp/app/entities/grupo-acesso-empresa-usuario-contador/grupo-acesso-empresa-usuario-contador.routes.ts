import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GrupoAcessoEmpresaUsuarioContadorComponent } from './list/grupo-acesso-empresa-usuario-contador.component';
import { GrupoAcessoEmpresaUsuarioContadorDetailComponent } from './detail/grupo-acesso-empresa-usuario-contador-detail.component';
import { GrupoAcessoEmpresaUsuarioContadorUpdateComponent } from './update/grupo-acesso-empresa-usuario-contador-update.component';
import GrupoAcessoEmpresaUsuarioContadorResolve from './route/grupo-acesso-empresa-usuario-contador-routing-resolve.service';

const grupoAcessoEmpresaUsuarioContadorRoute: Routes = [
  {
    path: '',
    component: GrupoAcessoEmpresaUsuarioContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrupoAcessoEmpresaUsuarioContadorDetailComponent,
    resolve: {
      grupoAcessoEmpresaUsuarioContador: GrupoAcessoEmpresaUsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrupoAcessoEmpresaUsuarioContadorUpdateComponent,
    resolve: {
      grupoAcessoEmpresaUsuarioContador: GrupoAcessoEmpresaUsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrupoAcessoEmpresaUsuarioContadorUpdateComponent,
    resolve: {
      grupoAcessoEmpresaUsuarioContador: GrupoAcessoEmpresaUsuarioContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default grupoAcessoEmpresaUsuarioContadorRoute;
