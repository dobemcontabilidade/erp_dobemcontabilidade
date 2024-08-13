import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServicoContabilComponent } from './list/servico-contabil.component';
import { ServicoContabilDetailComponent } from './detail/servico-contabil-detail.component';
import { ServicoContabilUpdateComponent } from './update/servico-contabil-update.component';
import ServicoContabilResolve from './route/servico-contabil-routing-resolve.service';

const servicoContabilRoute: Routes = [
  {
    path: '',
    component: ServicoContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServicoContabilDetailComponent,
    resolve: {
      servicoContabil: ServicoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServicoContabilUpdateComponent,
    resolve: {
      servicoContabil: ServicoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServicoContabilUpdateComponent,
    resolve: {
      servicoContabil: ServicoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicoContabilRoute;
