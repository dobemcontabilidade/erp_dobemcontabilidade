import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FeedBackContadorParaUsuarioComponent } from './list/feed-back-contador-para-usuario.component';
import { FeedBackContadorParaUsuarioDetailComponent } from './detail/feed-back-contador-para-usuario-detail.component';
import { FeedBackContadorParaUsuarioUpdateComponent } from './update/feed-back-contador-para-usuario-update.component';
import FeedBackContadorParaUsuarioResolve from './route/feed-back-contador-para-usuario-routing-resolve.service';

const feedBackContadorParaUsuarioRoute: Routes = [
  {
    path: '',
    component: FeedBackContadorParaUsuarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeedBackContadorParaUsuarioDetailComponent,
    resolve: {
      feedBackContadorParaUsuario: FeedBackContadorParaUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeedBackContadorParaUsuarioUpdateComponent,
    resolve: {
      feedBackContadorParaUsuario: FeedBackContadorParaUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeedBackContadorParaUsuarioUpdateComponent,
    resolve: {
      feedBackContadorParaUsuario: FeedBackContadorParaUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default feedBackContadorParaUsuarioRoute;
