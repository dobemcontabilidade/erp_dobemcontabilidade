import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AdicionalTributacaoComponent } from './list/adicional-tributacao.component';
import { AdicionalTributacaoDetailComponent } from './detail/adicional-tributacao-detail.component';
import { AdicionalTributacaoUpdateComponent } from './update/adicional-tributacao-update.component';
import AdicionalTributacaoResolve from './route/adicional-tributacao-routing-resolve.service';

const adicionalTributacaoRoute: Routes = [
  {
    path: '',
    component: AdicionalTributacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdicionalTributacaoDetailComponent,
    resolve: {
      adicionalTributacao: AdicionalTributacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdicionalTributacaoUpdateComponent,
    resolve: {
      adicionalTributacao: AdicionalTributacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdicionalTributacaoUpdateComponent,
    resolve: {
      adicionalTributacao: AdicionalTributacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default adicionalTributacaoRoute;
