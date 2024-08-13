import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FeedBackUsuarioParaContadorComponent } from './list/feed-back-usuario-para-contador.component';
import { FeedBackUsuarioParaContadorDetailComponent } from './detail/feed-back-usuario-para-contador-detail.component';
import { FeedBackUsuarioParaContadorUpdateComponent } from './update/feed-back-usuario-para-contador-update.component';
import FeedBackUsuarioParaContadorResolve from './route/feed-back-usuario-para-contador-routing-resolve.service';

const feedBackUsuarioParaContadorRoute: Routes = [
  {
    path: '',
    component: FeedBackUsuarioParaContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeedBackUsuarioParaContadorDetailComponent,
    resolve: {
      feedBackUsuarioParaContador: FeedBackUsuarioParaContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeedBackUsuarioParaContadorUpdateComponent,
    resolve: {
      feedBackUsuarioParaContador: FeedBackUsuarioParaContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeedBackUsuarioParaContadorUpdateComponent,
    resolve: {
      feedBackUsuarioParaContador: FeedBackUsuarioParaContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default feedBackUsuarioParaContadorRoute;
