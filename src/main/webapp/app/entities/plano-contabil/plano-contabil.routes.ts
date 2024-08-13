import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PlanoContabilComponent } from './list/plano-contabil.component';
import { PlanoContabilDetailComponent } from './detail/plano-contabil-detail.component';
import { PlanoContabilUpdateComponent } from './update/plano-contabil-update.component';
import PlanoContabilResolve from './route/plano-contabil-routing-resolve.service';

const planoContabilRoute: Routes = [
  {
    path: '',
    component: PlanoContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoContabilDetailComponent,
    resolve: {
      planoContabil: PlanoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoContabilUpdateComponent,
    resolve: {
      planoContabil: PlanoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoContabilUpdateComponent,
    resolve: {
      planoContabil: PlanoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default planoContabilRoute;
