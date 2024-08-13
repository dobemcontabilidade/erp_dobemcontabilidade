import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AgenteIntegracaoEstagioComponent } from './list/agente-integracao-estagio.component';
import { AgenteIntegracaoEstagioDetailComponent } from './detail/agente-integracao-estagio-detail.component';
import { AgenteIntegracaoEstagioUpdateComponent } from './update/agente-integracao-estagio-update.component';
import AgenteIntegracaoEstagioResolve from './route/agente-integracao-estagio-routing-resolve.service';

const agenteIntegracaoEstagioRoute: Routes = [
  {
    path: '',
    component: AgenteIntegracaoEstagioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgenteIntegracaoEstagioDetailComponent,
    resolve: {
      agenteIntegracaoEstagio: AgenteIntegracaoEstagioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgenteIntegracaoEstagioUpdateComponent,
    resolve: {
      agenteIntegracaoEstagio: AgenteIntegracaoEstagioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgenteIntegracaoEstagioUpdateComponent,
    resolve: {
      agenteIntegracaoEstagio: AgenteIntegracaoEstagioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default agenteIntegracaoEstagioRoute;
