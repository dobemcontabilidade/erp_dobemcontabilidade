import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaOrdemServicoComponent } from './list/tarefa-ordem-servico.component';
import { TarefaOrdemServicoDetailComponent } from './detail/tarefa-ordem-servico-detail.component';
import { TarefaOrdemServicoUpdateComponent } from './update/tarefa-ordem-servico-update.component';
import TarefaOrdemServicoResolve from './route/tarefa-ordem-servico-routing-resolve.service';

const tarefaOrdemServicoRoute: Routes = [
  {
    path: '',
    component: TarefaOrdemServicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaOrdemServicoDetailComponent,
    resolve: {
      tarefaOrdemServico: TarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaOrdemServicoUpdateComponent,
    resolve: {
      tarefaOrdemServico: TarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaOrdemServicoUpdateComponent,
    resolve: {
      tarefaOrdemServico: TarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaOrdemServicoRoute;
