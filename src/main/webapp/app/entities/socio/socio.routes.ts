import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SocioComponent } from './list/socio.component';
import { SocioDetailComponent } from './detail/socio-detail.component';
import { SocioUpdateComponent } from './update/socio-update.component';
import SocioResolve from './route/socio-routing-resolve.service';

const socioRoute: Routes = [
  {
    path: '',
    component: SocioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocioDetailComponent,
    resolve: {
      socio: SocioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocioUpdateComponent,
    resolve: {
      socio: SocioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocioUpdateComponent,
    resolve: {
      socio: SocioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default socioRoute;
