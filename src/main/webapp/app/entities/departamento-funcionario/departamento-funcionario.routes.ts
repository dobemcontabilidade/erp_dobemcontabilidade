import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DepartamentoFuncionarioComponent } from './list/departamento-funcionario.component';
import { DepartamentoFuncionarioDetailComponent } from './detail/departamento-funcionario-detail.component';
import { DepartamentoFuncionarioUpdateComponent } from './update/departamento-funcionario-update.component';
import DepartamentoFuncionarioResolve from './route/departamento-funcionario-routing-resolve.service';

const departamentoFuncionarioRoute: Routes = [
  {
    path: '',
    component: DepartamentoFuncionarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepartamentoFuncionarioDetailComponent,
    resolve: {
      departamentoFuncionario: DepartamentoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepartamentoFuncionarioUpdateComponent,
    resolve: {
      departamentoFuncionario: DepartamentoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepartamentoFuncionarioUpdateComponent,
    resolve: {
      departamentoFuncionario: DepartamentoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default departamentoFuncionarioRoute;
