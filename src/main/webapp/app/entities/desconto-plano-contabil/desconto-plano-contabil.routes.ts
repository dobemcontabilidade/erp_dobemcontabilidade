import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DescontoPlanoContabilComponent } from './list/desconto-plano-contabil.component';
import { DescontoPlanoContabilDetailComponent } from './detail/desconto-plano-contabil-detail.component';
import { DescontoPlanoContabilUpdateComponent } from './update/desconto-plano-contabil-update.component';
import DescontoPlanoContabilResolve from './route/desconto-plano-contabil-routing-resolve.service';

const descontoPlanoContabilRoute: Routes = [
  {
    path: '',
    component: DescontoPlanoContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DescontoPlanoContabilDetailComponent,
    resolve: {
      descontoPlanoContabil: DescontoPlanoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DescontoPlanoContabilUpdateComponent,
    resolve: {
      descontoPlanoContabil: DescontoPlanoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DescontoPlanoContabilUpdateComponent,
    resolve: {
      descontoPlanoContabil: DescontoPlanoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default descontoPlanoContabilRoute;
