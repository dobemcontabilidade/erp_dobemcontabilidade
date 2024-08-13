import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PerfilAcessoComponent } from './list/perfil-acesso.component';
import { PerfilAcessoDetailComponent } from './detail/perfil-acesso-detail.component';
import { PerfilAcessoUpdateComponent } from './update/perfil-acesso-update.component';
import PerfilAcessoResolve from './route/perfil-acesso-routing-resolve.service';

const perfilAcessoRoute: Routes = [
  {
    path: '',
    component: PerfilAcessoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfilAcessoDetailComponent,
    resolve: {
      perfilAcesso: PerfilAcessoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfilAcessoUpdateComponent,
    resolve: {
      perfilAcesso: PerfilAcessoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfilAcessoUpdateComponent,
    resolve: {
      perfilAcesso: PerfilAcessoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default perfilAcessoRoute;
