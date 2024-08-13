import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TermoDeAdesaoComponent } from './list/termo-de-adesao.component';
import { TermoDeAdesaoDetailComponent } from './detail/termo-de-adesao-detail.component';
import { TermoDeAdesaoUpdateComponent } from './update/termo-de-adesao-update.component';
import TermoDeAdesaoResolve from './route/termo-de-adesao-routing-resolve.service';

const termoDeAdesaoRoute: Routes = [
  {
    path: '',
    component: TermoDeAdesaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TermoDeAdesaoDetailComponent,
    resolve: {
      termoDeAdesao: TermoDeAdesaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TermoDeAdesaoUpdateComponent,
    resolve: {
      termoDeAdesao: TermoDeAdesaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TermoDeAdesaoUpdateComponent,
    resolve: {
      termoDeAdesao: TermoDeAdesaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default termoDeAdesaoRoute;
