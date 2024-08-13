import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RamoComponent } from './list/ramo.component';
import { RamoDetailComponent } from './detail/ramo-detail.component';
import { RamoUpdateComponent } from './update/ramo-update.component';
import RamoResolve from './route/ramo-routing-resolve.service';

const ramoRoute: Routes = [
  {
    path: '',
    component: RamoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RamoDetailComponent,
    resolve: {
      ramo: RamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RamoUpdateComponent,
    resolve: {
      ramo: RamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RamoUpdateComponent,
    resolve: {
      ramo: RamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ramoRoute;
