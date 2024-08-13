import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FluxoModeloComponent } from './list/fluxo-modelo.component';
import { FluxoModeloDetailComponent } from './detail/fluxo-modelo-detail.component';
import { FluxoModeloUpdateComponent } from './update/fluxo-modelo-update.component';
import FluxoModeloResolve from './route/fluxo-modelo-routing-resolve.service';

const fluxoModeloRoute: Routes = [
  {
    path: '',
    component: FluxoModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FluxoModeloDetailComponent,
    resolve: {
      fluxoModelo: FluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FluxoModeloUpdateComponent,
    resolve: {
      fluxoModelo: FluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FluxoModeloUpdateComponent,
    resolve: {
      fluxoModelo: FluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fluxoModeloRoute;
