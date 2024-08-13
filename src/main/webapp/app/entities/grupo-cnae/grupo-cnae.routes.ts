import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GrupoCnaeComponent } from './list/grupo-cnae.component';
import { GrupoCnaeDetailComponent } from './detail/grupo-cnae-detail.component';
import { GrupoCnaeUpdateComponent } from './update/grupo-cnae-update.component';
import GrupoCnaeResolve from './route/grupo-cnae-routing-resolve.service';

const grupoCnaeRoute: Routes = [
  {
    path: '',
    component: GrupoCnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrupoCnaeDetailComponent,
    resolve: {
      grupoCnae: GrupoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrupoCnaeUpdateComponent,
    resolve: {
      grupoCnae: GrupoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrupoCnaeUpdateComponent,
    resolve: {
      grupoCnae: GrupoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default grupoCnaeRoute;
