import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RedeSocialComponent } from './list/rede-social.component';
import { RedeSocialDetailComponent } from './detail/rede-social-detail.component';
import { RedeSocialUpdateComponent } from './update/rede-social-update.component';
import RedeSocialResolve from './route/rede-social-routing-resolve.service';

const redeSocialRoute: Routes = [
  {
    path: '',
    component: RedeSocialComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RedeSocialDetailComponent,
    resolve: {
      redeSocial: RedeSocialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RedeSocialUpdateComponent,
    resolve: {
      redeSocial: RedeSocialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RedeSocialUpdateComponent,
    resolve: {
      redeSocial: RedeSocialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default redeSocialRoute;
