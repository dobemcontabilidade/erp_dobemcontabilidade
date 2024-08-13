import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoOrdemServicoExecucaoComponent } from './list/anexo-ordem-servico-execucao.component';
import { AnexoOrdemServicoExecucaoDetailComponent } from './detail/anexo-ordem-servico-execucao-detail.component';
import { AnexoOrdemServicoExecucaoUpdateComponent } from './update/anexo-ordem-servico-execucao-update.component';
import AnexoOrdemServicoExecucaoResolve from './route/anexo-ordem-servico-execucao-routing-resolve.service';

const anexoOrdemServicoExecucaoRoute: Routes = [
  {
    path: '',
    component: AnexoOrdemServicoExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoOrdemServicoExecucaoDetailComponent,
    resolve: {
      anexoOrdemServicoExecucao: AnexoOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoOrdemServicoExecucaoUpdateComponent,
    resolve: {
      anexoOrdemServicoExecucao: AnexoOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoOrdemServicoExecucaoUpdateComponent,
    resolve: {
      anexoOrdemServicoExecucao: AnexoOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoOrdemServicoExecucaoRoute;
