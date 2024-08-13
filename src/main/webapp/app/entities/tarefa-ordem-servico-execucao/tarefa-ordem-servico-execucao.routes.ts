import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaOrdemServicoExecucaoComponent } from './list/tarefa-ordem-servico-execucao.component';
import { TarefaOrdemServicoExecucaoDetailComponent } from './detail/tarefa-ordem-servico-execucao-detail.component';
import { TarefaOrdemServicoExecucaoUpdateComponent } from './update/tarefa-ordem-servico-execucao-update.component';
import TarefaOrdemServicoExecucaoResolve from './route/tarefa-ordem-servico-execucao-routing-resolve.service';

const tarefaOrdemServicoExecucaoRoute: Routes = [
  {
    path: '',
    component: TarefaOrdemServicoExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaOrdemServicoExecucaoDetailComponent,
    resolve: {
      tarefaOrdemServicoExecucao: TarefaOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaOrdemServicoExecucaoUpdateComponent,
    resolve: {
      tarefaOrdemServicoExecucao: TarefaOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaOrdemServicoExecucaoUpdateComponent,
    resolve: {
      tarefaOrdemServicoExecucao: TarefaOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaOrdemServicoExecucaoRoute;
