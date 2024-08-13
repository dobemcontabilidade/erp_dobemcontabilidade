import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContadorResponsavelOrdemServicoComponent } from './list/contador-responsavel-ordem-servico.component';
import { ContadorResponsavelOrdemServicoDetailComponent } from './detail/contador-responsavel-ordem-servico-detail.component';
import { ContadorResponsavelOrdemServicoUpdateComponent } from './update/contador-responsavel-ordem-servico-update.component';
import ContadorResponsavelOrdemServicoResolve from './route/contador-responsavel-ordem-servico-routing-resolve.service';

const contadorResponsavelOrdemServicoRoute: Routes = [
  {
    path: '',
    component: ContadorResponsavelOrdemServicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContadorResponsavelOrdemServicoDetailComponent,
    resolve: {
      contadorResponsavelOrdemServico: ContadorResponsavelOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContadorResponsavelOrdemServicoUpdateComponent,
    resolve: {
      contadorResponsavelOrdemServico: ContadorResponsavelOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContadorResponsavelOrdemServicoUpdateComponent,
    resolve: {
      contadorResponsavelOrdemServico: ContadorResponsavelOrdemServicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contadorResponsavelOrdemServicoRoute;
