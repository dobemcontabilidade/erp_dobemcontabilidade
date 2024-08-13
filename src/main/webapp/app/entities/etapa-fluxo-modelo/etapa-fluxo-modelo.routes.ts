import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EtapaFluxoModeloComponent } from './list/etapa-fluxo-modelo.component';
import { EtapaFluxoModeloDetailComponent } from './detail/etapa-fluxo-modelo-detail.component';
import { EtapaFluxoModeloUpdateComponent } from './update/etapa-fluxo-modelo-update.component';
import EtapaFluxoModeloResolve from './route/etapa-fluxo-modelo-routing-resolve.service';

const etapaFluxoModeloRoute: Routes = [
  {
    path: '',
    component: EtapaFluxoModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtapaFluxoModeloDetailComponent,
    resolve: {
      etapaFluxoModelo: EtapaFluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtapaFluxoModeloUpdateComponent,
    resolve: {
      etapaFluxoModelo: EtapaFluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtapaFluxoModeloUpdateComponent,
    resolve: {
      etapaFluxoModelo: EtapaFluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default etapaFluxoModeloRoute;
