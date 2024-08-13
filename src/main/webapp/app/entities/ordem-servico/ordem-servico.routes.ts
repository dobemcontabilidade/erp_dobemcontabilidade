import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { OrdemServicoComponent } from './list/ordem-servico.component';
import { OrdemServicoDetailComponent } from './detail/ordem-servico-detail.component';
import { OrdemServicoUpdateComponent } from './update/ordem-servico-update.component';
import OrdemServicoResolve from './route/ordem-servico-routing-resolve.service';

const ordemServicoRoute: Routes = [
  {
    path: '',
    component: OrdemServicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrdemServicoDetailComponent,
    resolve: {
      ordemServico: OrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrdemServicoUpdateComponent,
    resolve: {
      ordemServico: OrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrdemServicoUpdateComponent,
    resolve: {
      ordemServico: OrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ordemServicoRoute;
