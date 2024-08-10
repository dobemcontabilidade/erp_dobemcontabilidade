import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PerfilRedeSocialComponent } from './list/perfil-rede-social.component';
import { PerfilRedeSocialDetailComponent } from './detail/perfil-rede-social-detail.component';
import { PerfilRedeSocialUpdateComponent } from './update/perfil-rede-social-update.component';
import PerfilRedeSocialResolve from './route/perfil-rede-social-routing-resolve.service';

const perfilRedeSocialRoute: Routes = [
  {
    path: '',
    component: PerfilRedeSocialComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfilRedeSocialDetailComponent,
    resolve: {
      perfilRedeSocial: PerfilRedeSocialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfilRedeSocialUpdateComponent,
    resolve: {
      perfilRedeSocial: PerfilRedeSocialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfilRedeSocialUpdateComponent,
    resolve: {
      perfilRedeSocial: PerfilRedeSocialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default perfilRedeSocialRoute;
