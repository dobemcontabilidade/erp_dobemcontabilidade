import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AreaContabilContadorComponent } from './list/area-contabil-contador.component';
import { AreaContabilContadorDetailComponent } from './detail/area-contabil-contador-detail.component';
import { AreaContabilContadorUpdateComponent } from './update/area-contabil-contador-update.component';
import AreaContabilContadorResolve from './route/area-contabil-contador-routing-resolve.service';

const areaContabilContadorRoute: Routes = [
  {
    path: '',
    component: AreaContabilContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AreaContabilContadorDetailComponent,
    resolve: {
      areaContabilContador: AreaContabilContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaContabilContadorUpdateComponent,
    resolve: {
      areaContabilContador: AreaContabilContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AreaContabilContadorUpdateComponent,
    resolve: {
      areaContabilContador: AreaContabilContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default areaContabilContadorRoute;
