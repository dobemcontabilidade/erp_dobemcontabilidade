import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SecaoCnaeComponent } from './list/secao-cnae.component';
import { SecaoCnaeDetailComponent } from './detail/secao-cnae-detail.component';
import { SecaoCnaeUpdateComponent } from './update/secao-cnae-update.component';
import SecaoCnaeResolve from './route/secao-cnae-routing-resolve.service';

const secaoCnaeRoute: Routes = [
  {
    path: '',
    component: SecaoCnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecaoCnaeDetailComponent,
    resolve: {
      secaoCnae: SecaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecaoCnaeUpdateComponent,
    resolve: {
      secaoCnae: SecaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecaoCnaeUpdateComponent,
    resolve: {
      secaoCnae: SecaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default secaoCnaeRoute;
