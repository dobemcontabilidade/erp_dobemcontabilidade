import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EstrangeiroComponent } from './list/estrangeiro.component';
import { EstrangeiroDetailComponent } from './detail/estrangeiro-detail.component';
import { EstrangeiroUpdateComponent } from './update/estrangeiro-update.component';
import EstrangeiroResolve from './route/estrangeiro-routing-resolve.service';

const estrangeiroRoute: Routes = [
  {
    path: '',
    component: EstrangeiroComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstrangeiroDetailComponent,
    resolve: {
      estrangeiro: EstrangeiroResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstrangeiroUpdateComponent,
    resolve: {
      estrangeiro: EstrangeiroResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstrangeiroUpdateComponent,
    resolve: {
      estrangeiro: EstrangeiroResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default estrangeiroRoute;
