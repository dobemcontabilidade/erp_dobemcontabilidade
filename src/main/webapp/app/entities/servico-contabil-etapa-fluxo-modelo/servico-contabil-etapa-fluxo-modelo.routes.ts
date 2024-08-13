import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServicoContabilEtapaFluxoModeloComponent } from './list/servico-contabil-etapa-fluxo-modelo.component';
import { ServicoContabilEtapaFluxoModeloDetailComponent } from './detail/servico-contabil-etapa-fluxo-modelo-detail.component';
import { ServicoContabilEtapaFluxoModeloUpdateComponent } from './update/servico-contabil-etapa-fluxo-modelo-update.component';
import ServicoContabilEtapaFluxoModeloResolve from './route/servico-contabil-etapa-fluxo-modelo-routing-resolve.service';

const servicoContabilEtapaFluxoModeloRoute: Routes = [
  {
    path: '',
    component: ServicoContabilEtapaFluxoModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServicoContabilEtapaFluxoModeloDetailComponent,
    resolve: {
      servicoContabilEtapaFluxoModelo: ServicoContabilEtapaFluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServicoContabilEtapaFluxoModeloUpdateComponent,
    resolve: {
      servicoContabilEtapaFluxoModelo: ServicoContabilEtapaFluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServicoContabilEtapaFluxoModeloUpdateComponent,
    resolve: {
      servicoContabilEtapaFluxoModelo: ServicoContabilEtapaFluxoModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicoContabilEtapaFluxoModeloRoute;
