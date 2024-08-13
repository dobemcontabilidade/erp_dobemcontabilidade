import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EtapaFluxoExecucaoComponent } from './list/etapa-fluxo-execucao.component';
import { EtapaFluxoExecucaoDetailComponent } from './detail/etapa-fluxo-execucao-detail.component';
import { EtapaFluxoExecucaoUpdateComponent } from './update/etapa-fluxo-execucao-update.component';
import EtapaFluxoExecucaoResolve from './route/etapa-fluxo-execucao-routing-resolve.service';

const etapaFluxoExecucaoRoute: Routes = [
  {
    path: '',
    component: EtapaFluxoExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtapaFluxoExecucaoDetailComponent,
    resolve: {
      etapaFluxoExecucao: EtapaFluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtapaFluxoExecucaoUpdateComponent,
    resolve: {
      etapaFluxoExecucao: EtapaFluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtapaFluxoExecucaoUpdateComponent,
    resolve: {
      etapaFluxoExecucao: EtapaFluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default etapaFluxoExecucaoRoute;
