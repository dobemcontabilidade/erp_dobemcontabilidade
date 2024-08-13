import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DemissaoFuncionarioComponent } from './list/demissao-funcionario.component';
import { DemissaoFuncionarioDetailComponent } from './detail/demissao-funcionario-detail.component';
import { DemissaoFuncionarioUpdateComponent } from './update/demissao-funcionario-update.component';
import DemissaoFuncionarioResolve from './route/demissao-funcionario-routing-resolve.service';

const demissaoFuncionarioRoute: Routes = [
  {
    path: '',
    component: DemissaoFuncionarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemissaoFuncionarioDetailComponent,
    resolve: {
      demissaoFuncionario: DemissaoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemissaoFuncionarioUpdateComponent,
    resolve: {
      demissaoFuncionario: DemissaoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemissaoFuncionarioUpdateComponent,
    resolve: {
      demissaoFuncionario: DemissaoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default demissaoFuncionarioRoute;
