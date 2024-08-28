import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AdicionalRamoComponent } from './list/adicional-ramo.component';
import { AdicionalRamoDetailComponent } from './detail/adicional-ramo-detail.component';
import { AdicionalRamoUpdateComponent } from './update/adicional-ramo-update.component';
import AdicionalRamoResolve from './route/adicional-ramo-routing-resolve.service';

const adicionalRamoRoute: Routes = [
  {
    path: '',
    component: AdicionalRamoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdicionalRamoDetailComponent,
    resolve: {
      adicionalRamo: AdicionalRamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdicionalRamoUpdateComponent,
    resolve: {
      adicionalRamo: AdicionalRamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdicionalRamoUpdateComponent,
    resolve: {
      adicionalRamo: AdicionalRamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default adicionalRamoRoute;
