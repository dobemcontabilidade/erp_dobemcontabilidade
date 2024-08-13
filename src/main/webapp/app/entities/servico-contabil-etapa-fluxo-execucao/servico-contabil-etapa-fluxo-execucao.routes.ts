import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServicoContabilEtapaFluxoExecucaoComponent } from './list/servico-contabil-etapa-fluxo-execucao.component';
import { ServicoContabilEtapaFluxoExecucaoDetailComponent } from './detail/servico-contabil-etapa-fluxo-execucao-detail.component';
import { ServicoContabilEtapaFluxoExecucaoUpdateComponent } from './update/servico-contabil-etapa-fluxo-execucao-update.component';
import ServicoContabilEtapaFluxoExecucaoResolve from './route/servico-contabil-etapa-fluxo-execucao-routing-resolve.service';

const servicoContabilEtapaFluxoExecucaoRoute: Routes = [
  {
    path: '',
    component: ServicoContabilEtapaFluxoExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServicoContabilEtapaFluxoExecucaoDetailComponent,
    resolve: {
      servicoContabilEtapaFluxoExecucao: ServicoContabilEtapaFluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServicoContabilEtapaFluxoExecucaoUpdateComponent,
    resolve: {
      servicoContabilEtapaFluxoExecucao: ServicoContabilEtapaFluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServicoContabilEtapaFluxoExecucaoUpdateComponent,
    resolve: {
      servicoContabilEtapaFluxoExecucao: ServicoContabilEtapaFluxoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicoContabilEtapaFluxoExecucaoRoute;
