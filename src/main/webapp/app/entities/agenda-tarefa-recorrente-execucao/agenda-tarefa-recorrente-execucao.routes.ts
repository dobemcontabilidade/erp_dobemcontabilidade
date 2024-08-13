import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AgendaTarefaRecorrenteExecucaoComponent } from './list/agenda-tarefa-recorrente-execucao.component';
import { AgendaTarefaRecorrenteExecucaoDetailComponent } from './detail/agenda-tarefa-recorrente-execucao-detail.component';
import { AgendaTarefaRecorrenteExecucaoUpdateComponent } from './update/agenda-tarefa-recorrente-execucao-update.component';
import AgendaTarefaRecorrenteExecucaoResolve from './route/agenda-tarefa-recorrente-execucao-routing-resolve.service';

const agendaTarefaRecorrenteExecucaoRoute: Routes = [
  {
    path: '',
    component: AgendaTarefaRecorrenteExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgendaTarefaRecorrenteExecucaoDetailComponent,
    resolve: {
      agendaTarefaRecorrenteExecucao: AgendaTarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgendaTarefaRecorrenteExecucaoUpdateComponent,
    resolve: {
      agendaTarefaRecorrenteExecucao: AgendaTarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgendaTarefaRecorrenteExecucaoUpdateComponent,
    resolve: {
      agendaTarefaRecorrenteExecucao: AgendaTarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default agendaTarefaRecorrenteExecucaoRoute;
