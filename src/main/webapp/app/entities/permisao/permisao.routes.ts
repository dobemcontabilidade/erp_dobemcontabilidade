import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PermisaoComponent } from './list/permisao.component';
import { PermisaoDetailComponent } from './detail/permisao-detail.component';
import { PermisaoUpdateComponent } from './update/permisao-update.component';
import PermisaoResolve from './route/permisao-routing-resolve.service';

const permisaoRoute: Routes = [
  {
    path: '',
    component: PermisaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PermisaoDetailComponent,
    resolve: {
      permisao: PermisaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PermisaoUpdateComponent,
    resolve: {
      permisao: PermisaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PermisaoUpdateComponent,
    resolve: {
      permisao: PermisaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default permisaoRoute;
