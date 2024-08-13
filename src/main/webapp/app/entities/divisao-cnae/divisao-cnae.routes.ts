import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DivisaoCnaeComponent } from './list/divisao-cnae.component';
import { DivisaoCnaeDetailComponent } from './detail/divisao-cnae-detail.component';
import { DivisaoCnaeUpdateComponent } from './update/divisao-cnae-update.component';
import DivisaoCnaeResolve from './route/divisao-cnae-routing-resolve.service';

const divisaoCnaeRoute: Routes = [
  {
    path: '',
    component: DivisaoCnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DivisaoCnaeDetailComponent,
    resolve: {
      divisaoCnae: DivisaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DivisaoCnaeUpdateComponent,
    resolve: {
      divisaoCnae: DivisaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DivisaoCnaeUpdateComponent,
    resolve: {
      divisaoCnae: DivisaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default divisaoCnaeRoute;
