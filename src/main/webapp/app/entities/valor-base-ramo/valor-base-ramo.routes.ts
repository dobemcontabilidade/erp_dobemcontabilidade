import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ValorBaseRamoComponent } from './list/valor-base-ramo.component';
import { ValorBaseRamoDetailComponent } from './detail/valor-base-ramo-detail.component';
import { ValorBaseRamoUpdateComponent } from './update/valor-base-ramo-update.component';
import ValorBaseRamoResolve from './route/valor-base-ramo-routing-resolve.service';

const valorBaseRamoRoute: Routes = [
  {
    path: '',
    component: ValorBaseRamoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ValorBaseRamoDetailComponent,
    resolve: {
      valorBaseRamo: ValorBaseRamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ValorBaseRamoUpdateComponent,
    resolve: {
      valorBaseRamo: ValorBaseRamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ValorBaseRamoUpdateComponent,
    resolve: {
      valorBaseRamo: ValorBaseRamoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default valorBaseRamoRoute;
