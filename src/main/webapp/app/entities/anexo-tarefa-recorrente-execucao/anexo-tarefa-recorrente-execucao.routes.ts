import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoTarefaRecorrenteExecucaoComponent } from './list/anexo-tarefa-recorrente-execucao.component';
import { AnexoTarefaRecorrenteExecucaoDetailComponent } from './detail/anexo-tarefa-recorrente-execucao-detail.component';
import { AnexoTarefaRecorrenteExecucaoUpdateComponent } from './update/anexo-tarefa-recorrente-execucao-update.component';
import AnexoTarefaRecorrenteExecucaoResolve from './route/anexo-tarefa-recorrente-execucao-routing-resolve.service';

const anexoTarefaRecorrenteExecucaoRoute: Routes = [
  {
    path: '',
    component: AnexoTarefaRecorrenteExecucaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoTarefaRecorrenteExecucaoDetailComponent,
    resolve: {
      anexoTarefaRecorrenteExecucao: AnexoTarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoTarefaRecorrenteExecucaoUpdateComponent,
    resolve: {
      anexoTarefaRecorrenteExecucao: AnexoTarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoTarefaRecorrenteExecucaoUpdateComponent,
    resolve: {
      anexoTarefaRecorrenteExecucao: AnexoTarefaRecorrenteExecucaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoTarefaRecorrenteExecucaoRoute;
