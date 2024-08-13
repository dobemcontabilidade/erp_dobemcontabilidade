import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GrupoAcessoUsuarioEmpresaComponent } from './list/grupo-acesso-usuario-empresa.component';
import { GrupoAcessoUsuarioEmpresaDetailComponent } from './detail/grupo-acesso-usuario-empresa-detail.component';
import { GrupoAcessoUsuarioEmpresaUpdateComponent } from './update/grupo-acesso-usuario-empresa-update.component';
import GrupoAcessoUsuarioEmpresaResolve from './route/grupo-acesso-usuario-empresa-routing-resolve.service';

const grupoAcessoUsuarioEmpresaRoute: Routes = [
  {
    path: '',
    component: GrupoAcessoUsuarioEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrupoAcessoUsuarioEmpresaDetailComponent,
    resolve: {
      grupoAcessoUsuarioEmpresa: GrupoAcessoUsuarioEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrupoAcessoUsuarioEmpresaUpdateComponent,
    resolve: {
      grupoAcessoUsuarioEmpresa: GrupoAcessoUsuarioEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrupoAcessoUsuarioEmpresaUpdateComponent,
    resolve: {
      grupoAcessoUsuarioEmpresa: GrupoAcessoUsuarioEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default grupoAcessoUsuarioEmpresaRoute;
