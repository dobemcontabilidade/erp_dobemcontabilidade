import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaRecorrenteExecucaoComponent } from './list/tarefa-recorrente-execucao.component';
import { TarefaRecorrenteExecucaoDetailComponent } from './detail/tarefa-recorrente-execucao-detail.component';
import { TarefaRecorrenteExecucaoUpdateComponent } from './update/tarefa-recorrente-execucao-update.component';
import TarefaRecorrenteExecucaoResolve from './route/tarefa-recorrente-execucao-routing-resolve.service';

const tarefaRecorrenteExecucaoRoute: Routes = [
  {
    path: '',
    component: TarefaRecorrenteExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaRecorrenteExecucaoDetailComponent,
    resolve: {
      tarefaRecorrenteExecucao: TarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaRecorrenteExecucaoUpdateComponent,
    resolve: {
      tarefaRecorrenteExecucao: TarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaRecorrenteExecucaoUpdateComponent,
    resolve: {
      tarefaRecorrenteExecucao: TarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaRecorrenteExecucaoRoute;
