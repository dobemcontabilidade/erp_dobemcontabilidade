import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContratoFuncionarioComponent } from './list/contrato-funcionario.component';
import { ContratoFuncionarioDetailComponent } from './detail/contrato-funcionario-detail.component';
import { ContratoFuncionarioUpdateComponent } from './update/contrato-funcionario-update.component';
import ContratoFuncionarioResolve from './route/contrato-funcionario-routing-resolve.service';

const contratoFuncionarioRoute: Routes = [
  {
    path: '',
    component: ContratoFuncionarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContratoFuncionarioDetailComponent,
    resolve: {
      contratoFuncionario: ContratoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContratoFuncionarioUpdateComponent,
    resolve: {
      contratoFuncionario: ContratoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContratoFuncionarioUpdateComponent,
    resolve: {
      contratoFuncionario: ContratoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contratoFuncionarioRoute;
