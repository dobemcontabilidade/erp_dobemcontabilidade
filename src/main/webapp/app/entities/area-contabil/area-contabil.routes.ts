import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AreaContabilComponent } from './list/area-contabil.component';
import { AreaContabilDetailComponent } from './detail/area-contabil-detail.component';
import { AreaContabilUpdateComponent } from './update/area-contabil-update.component';
import AreaContabilResolve from './route/area-contabil-routing-resolve.service';

const areaContabilRoute: Routes = [
  {
    path: '',
    component: AreaContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AreaContabilDetailComponent,
    resolve: {
      areaContabil: AreaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaContabilUpdateComponent,
    resolve: {
      areaContabil: AreaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AreaContabilUpdateComponent,
    resolve: {
      areaContabil: AreaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default areaContabilRoute;
