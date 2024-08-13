import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FluxoExecucaoComponent } from './list/fluxo-execucao.component';
import { FluxoExecucaoDetailComponent } from './detail/fluxo-execucao-detail.component';
import { FluxoExecucaoUpdateComponent } from './update/fluxo-execucao-update.component';
import FluxoExecucaoResolve from './route/fluxo-execucao-routing-resolve.service';

const fluxoExecucaoRoute: Routes = [
  {
    path: '',
    component: FluxoExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FluxoExecucaoDetailComponent,
    resolve: {
      fluxoExecucao: FluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FluxoExecucaoUpdateComponent,
    resolve: {
      fluxoExecucao: FluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FluxoExecucaoUpdateComponent,
    resolve: {
      fluxoExecucao: FluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fluxoExecucaoRoute;
