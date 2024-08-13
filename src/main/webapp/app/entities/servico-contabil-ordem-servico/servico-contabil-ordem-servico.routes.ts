import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServicoContabilOrdemServicoComponent } from './list/servico-contabil-ordem-servico.component';
import { ServicoContabilOrdemServicoDetailComponent } from './detail/servico-contabil-ordem-servico-detail.component';
import { ServicoContabilOrdemServicoUpdateComponent } from './update/servico-contabil-ordem-servico-update.component';
import ServicoContabilOrdemServicoResolve from './route/servico-contabil-ordem-servico-routing-resolve.service';

const servicoContabilOrdemServicoRoute: Routes = [
  {
    path: '',
    component: ServicoContabilOrdemServicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServicoContabilOrdemServicoDetailComponent,
    resolve: {
      servicoContabilOrdemServico: ServicoContabilOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServicoContabilOrdemServicoUpdateComponent,
    resolve: {
      servicoContabilOrdemServico: ServicoContabilOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServicoContabilOrdemServicoUpdateComponent,
    resolve: {
      servicoContabilOrdemServico: ServicoContabilOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicoContabilOrdemServicoRoute;
