import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PerfilAcessoUsuarioComponent } from './list/perfil-acesso-usuario.component';
import { PerfilAcessoUsuarioDetailComponent } from './detail/perfil-acesso-usuario-detail.component';
import { PerfilAcessoUsuarioUpdateComponent } from './update/perfil-acesso-usuario-update.component';
import PerfilAcessoUsuarioResolve from './route/perfil-acesso-usuario-routing-resolve.service';

const perfilAcessoUsuarioRoute: Routes = [
  {
    path: '',
    component: PerfilAcessoUsuarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfilAcessoUsuarioDetailComponent,
    resolve: {
      perfilAcessoUsuario: PerfilAcessoUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfilAcessoUsuarioUpdateComponent,
    resolve: {
      perfilAcessoUsuario: PerfilAcessoUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfilAcessoUsuarioUpdateComponent,
    resolve: {
      perfilAcessoUsuario: PerfilAcessoUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default perfilAcessoUsuarioRoute;
