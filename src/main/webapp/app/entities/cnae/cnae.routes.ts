import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CnaeComponent } from './list/cnae.component';
import { CnaeDetailComponent } from './detail/cnae-detail.component';
import { CnaeUpdateComponent } from './update/cnae-update.component';
import CnaeResolve from './route/cnae-routing-resolve.service';

const cnaeRoute: Routes = [
  {
    path: '',
    component: CnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CnaeDetailComponent,
    resolve: {
      cnae: CnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CnaeUpdateComponent,
    resolve: {
      cnae: CnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CnaeUpdateComponent,
    resolve: {
      cnae: CnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cnaeRoute;
