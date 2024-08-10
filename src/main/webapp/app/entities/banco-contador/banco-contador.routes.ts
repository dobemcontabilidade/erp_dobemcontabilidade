import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BancoContadorComponent } from './list/banco-contador.component';
import { BancoContadorDetailComponent } from './detail/banco-contador-detail.component';
import { BancoContadorUpdateComponent } from './update/banco-contador-update.component';
import BancoContadorResolve from './route/banco-contador-routing-resolve.service';

const bancoContadorRoute: Routes = [
  {
    path: '',
    component: BancoContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BancoContadorDetailComponent,
    resolve: {
      bancoContador: BancoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BancoContadorUpdateComponent,
    resolve: {
      bancoContador: BancoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BancoContadorUpdateComponent,
    resolve: {
      bancoContador: BancoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bancoContadorRoute;
