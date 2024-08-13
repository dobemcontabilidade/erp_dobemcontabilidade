import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoRequeridoServicoContabilComponent } from './list/anexo-requerido-servico-contabil.component';
import { AnexoRequeridoServicoContabilDetailComponent } from './detail/anexo-requerido-servico-contabil-detail.component';
import { AnexoRequeridoServicoContabilUpdateComponent } from './update/anexo-requerido-servico-contabil-update.component';
import AnexoRequeridoServicoContabilResolve from './route/anexo-requerido-servico-contabil-routing-resolve.service';

const anexoRequeridoServicoContabilRoute: Routes = [
  {
    path: '',
    component: AnexoRequeridoServicoContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoRequeridoServicoContabilDetailComponent,
    resolve: {
      anexoRequeridoServicoContabil: AnexoRequeridoServicoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoRequeridoServicoContabilUpdateComponent,
    resolve: {
      anexoRequeridoServicoContabil: AnexoRequeridoServicoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoRequeridoServicoContabilUpdateComponent,
    resolve: {
      anexoRequeridoServicoContabil: AnexoRequeridoServicoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoRequeridoServicoContabilRoute;
