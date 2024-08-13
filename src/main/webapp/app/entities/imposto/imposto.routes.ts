import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ImpostoComponent } from './list/imposto.component';
import { ImpostoDetailComponent } from './detail/imposto-detail.component';
import { ImpostoUpdateComponent } from './update/imposto-update.component';
import ImpostoResolve from './route/imposto-routing-resolve.service';

const impostoRoute: Routes = [
  {
    path: '',
    component: ImpostoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImpostoDetailComponent,
    resolve: {
      imposto: ImpostoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImpostoUpdateComponent,
    resolve: {
      imposto: ImpostoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImpostoUpdateComponent,
    resolve: {
      imposto: ImpostoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default impostoRoute;
