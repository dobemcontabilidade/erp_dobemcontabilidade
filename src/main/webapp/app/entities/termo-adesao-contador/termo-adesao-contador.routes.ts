import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TermoAdesaoContadorComponent } from './list/termo-adesao-contador.component';
import { TermoAdesaoContadorDetailComponent } from './detail/termo-adesao-contador-detail.component';
import { TermoAdesaoContadorUpdateComponent } from './update/termo-adesao-contador-update.component';
import TermoAdesaoContadorResolve from './route/termo-adesao-contador-routing-resolve.service';

const termoAdesaoContadorRoute: Routes = [
  {
    path: '',
    component: TermoAdesaoContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TermoAdesaoContadorDetailComponent,
    resolve: {
      termoAdesaoContador: TermoAdesaoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TermoAdesaoContadorUpdateComponent,
    resolve: {
      termoAdesaoContador: TermoAdesaoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TermoAdesaoContadorUpdateComponent,
    resolve: {
      termoAdesaoContador: TermoAdesaoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default termoAdesaoContadorRoute;
