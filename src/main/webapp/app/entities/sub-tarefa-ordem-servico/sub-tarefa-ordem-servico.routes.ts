import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SubTarefaOrdemServicoComponent } from './list/sub-tarefa-ordem-servico.component';
import { SubTarefaOrdemServicoDetailComponent } from './detail/sub-tarefa-ordem-servico-detail.component';
import { SubTarefaOrdemServicoUpdateComponent } from './update/sub-tarefa-ordem-servico-update.component';
import SubTarefaOrdemServicoResolve from './route/sub-tarefa-ordem-servico-routing-resolve.service';

const subTarefaOrdemServicoRoute: Routes = [
  {
    path: '',
    component: SubTarefaOrdemServicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubTarefaOrdemServicoDetailComponent,
    resolve: {
      subTarefaOrdemServico: SubTarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubTarefaOrdemServicoUpdateComponent,
    resolve: {
      subTarefaOrdemServico: SubTarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubTarefaOrdemServicoUpdateComponent,
    resolve: {
      subTarefaOrdemServico: SubTarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subTarefaOrdemServicoRoute;
