import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DepartamentoEmpresaComponent } from './list/departamento-empresa.component';
import { DepartamentoEmpresaDetailComponent } from './detail/departamento-empresa-detail.component';
import { DepartamentoEmpresaUpdateComponent } from './update/departamento-empresa-update.component';
import DepartamentoEmpresaResolve from './route/departamento-empresa-routing-resolve.service';

const departamentoEmpresaRoute: Routes = [
  {
    path: '',
    component: DepartamentoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepartamentoEmpresaDetailComponent,
    resolve: {
      departamentoEmpresa: DepartamentoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepartamentoEmpresaUpdateComponent,
    resolve: {
      departamentoEmpresa: DepartamentoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepartamentoEmpresaUpdateComponent,
    resolve: {
      departamentoEmpresa: DepartamentoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default departamentoEmpresaRoute;
