import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EnquadramentoComponent } from './list/enquadramento.component';
import { EnquadramentoDetailComponent } from './detail/enquadramento-detail.component';
import { EnquadramentoUpdateComponent } from './update/enquadramento-update.component';
import EnquadramentoResolve from './route/enquadramento-routing-resolve.service';

const enquadramentoRoute: Routes = [
  {
    path: '',
    component: EnquadramentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnquadramentoDetailComponent,
    resolve: {
      enquadramento: EnquadramentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnquadramentoUpdateComponent,
    resolve: {
      enquadramento: EnquadramentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnquadramentoUpdateComponent,
    resolve: {
      enquadramento: EnquadramentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default enquadramentoRoute;
