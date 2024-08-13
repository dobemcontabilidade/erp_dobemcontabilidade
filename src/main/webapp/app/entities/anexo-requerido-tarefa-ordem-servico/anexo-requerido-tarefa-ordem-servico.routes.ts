import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoRequeridoTarefaOrdemServicoComponent } from './list/anexo-requerido-tarefa-ordem-servico.component';
import { AnexoRequeridoTarefaOrdemServicoDetailComponent } from './detail/anexo-requerido-tarefa-ordem-servico-detail.component';
import { AnexoRequeridoTarefaOrdemServicoUpdateComponent } from './update/anexo-requerido-tarefa-ordem-servico-update.component';
import AnexoRequeridoTarefaOrdemServicoResolve from './route/anexo-requerido-tarefa-ordem-servico-routing-resolve.service';

const anexoRequeridoTarefaOrdemServicoRoute: Routes = [
  {
    path: '',
    component: AnexoRequeridoTarefaOrdemServicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoRequeridoTarefaOrdemServicoDetailComponent,
    resolve: {
      anexoRequeridoTarefaOrdemServico: AnexoRequeridoTarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoRequeridoTarefaOrdemServicoUpdateComponent,
    resolve: {
      anexoRequeridoTarefaOrdemServico: AnexoRequeridoTarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoRequeridoTarefaOrdemServicoUpdateComponent,
    resolve: {
      anexoRequeridoTarefaOrdemServico: AnexoRequeridoTarefaOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoRequeridoTarefaOrdemServicoRoute;
