import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DepartamentoContadorComponent } from './list/departamento-contador.component';
import { DepartamentoContadorDetailComponent } from './detail/departamento-contador-detail.component';
import { DepartamentoContadorUpdateComponent } from './update/departamento-contador-update.component';
import DepartamentoContadorResolve from './route/departamento-contador-routing-resolve.service';

const departamentoContadorRoute: Routes = [
  {
    path: '',
    component: DepartamentoContadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepartamentoContadorDetailComponent,
    resolve: {
      departamentoContador: DepartamentoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepartamentoContadorUpdateComponent,
    resolve: {
      departamentoContador: DepartamentoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepartamentoContadorUpdateComponent,
    resolve: {
      departamentoContador: DepartamentoContadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default departamentoContadorRoute;
