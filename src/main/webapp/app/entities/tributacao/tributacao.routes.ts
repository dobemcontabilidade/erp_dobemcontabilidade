import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TributacaoComponent } from './list/tributacao.component';
import { TributacaoDetailComponent } from './detail/tributacao-detail.component';
import { TributacaoUpdateComponent } from './update/tributacao-update.component';
import TributacaoResolve from './route/tributacao-routing-resolve.service';

const tributacaoRoute: Routes = [
  {
    path: '',
    component: TributacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TributacaoDetailComponent,
    resolve: {
      tributacao: TributacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TributacaoUpdateComponent,
    resolve: {
      tributacao: TributacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TributacaoUpdateComponent,
    resolve: {
      tributacao: TributacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tributacaoRoute;
