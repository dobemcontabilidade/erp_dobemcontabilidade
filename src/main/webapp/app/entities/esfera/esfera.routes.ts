import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EsferaComponent } from './list/esfera.component';
import { EsferaDetailComponent } from './detail/esfera-detail.component';
import { EsferaUpdateComponent } from './update/esfera-update.component';
import EsferaResolve from './route/esfera-routing-resolve.service';

const esferaRoute: Routes = [
  {
    path: '',
    component: EsferaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EsferaDetailComponent,
    resolve: {
      esfera: EsferaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EsferaUpdateComponent,
    resolve: {
      esfera: EsferaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EsferaUpdateComponent,
    resolve: {
      esfera: EsferaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default esferaRoute;
