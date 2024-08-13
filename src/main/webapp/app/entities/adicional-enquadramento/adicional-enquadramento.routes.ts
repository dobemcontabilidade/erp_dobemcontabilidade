import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AdicionalEnquadramentoComponent } from './list/adicional-enquadramento.component';
import { AdicionalEnquadramentoDetailComponent } from './detail/adicional-enquadramento-detail.component';
import { AdicionalEnquadramentoUpdateComponent } from './update/adicional-enquadramento-update.component';
import AdicionalEnquadramentoResolve from './route/adicional-enquadramento-routing-resolve.service';

const adicionalEnquadramentoRoute: Routes = [
  {
    path: '',
    component: AdicionalEnquadramentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdicionalEnquadramentoDetailComponent,
    resolve: {
      adicionalEnquadramento: AdicionalEnquadramentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdicionalEnquadramentoUpdateComponent,
    resolve: {
      adicionalEnquadramento: AdicionalEnquadramentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdicionalEnquadramentoUpdateComponent,
    resolve: {
      adicionalEnquadramento: AdicionalEnquadramentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default adicionalEnquadramentoRoute;
