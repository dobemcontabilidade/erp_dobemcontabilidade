import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AgendaTarefaOrdemServicoExecucaoComponent } from './list/agenda-tarefa-ordem-servico-execucao.component';
import { AgendaTarefaOrdemServicoExecucaoDetailComponent } from './detail/agenda-tarefa-ordem-servico-execucao-detail.component';
import { AgendaTarefaOrdemServicoExecucaoUpdateComponent } from './update/agenda-tarefa-ordem-servico-execucao-update.component';
import AgendaTarefaOrdemServicoExecucaoResolve from './route/agenda-tarefa-ordem-servico-execucao-routing-resolve.service';

const agendaTarefaOrdemServicoExecucaoRoute: Routes = [
  {
    path: '',
    component: AgendaTarefaOrdemServicoExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgendaTarefaOrdemServicoExecucaoDetailComponent,
    resolve: {
      agendaTarefaOrdemServicoExecucao: AgendaTarefaOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgendaTarefaOrdemServicoExecucaoUpdateComponent,
    resolve: {
      agendaTarefaOrdemServicoExecucao: AgendaTarefaOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgendaTarefaOrdemServicoExecucaoUpdateComponent,
    resolve: {
      agendaTarefaOrdemServicoExecucao: AgendaTarefaOrdemServicoExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default agendaTarefaOrdemServicoExecucaoRoute;
