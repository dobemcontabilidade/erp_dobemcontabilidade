import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DependentesFuncionarioComponent } from './list/dependentes-funcionario.component';
import { DependentesFuncionarioDetailComponent } from './detail/dependentes-funcionario-detail.component';
import { DependentesFuncionarioUpdateComponent } from './update/dependentes-funcionario-update.component';
import DependentesFuncionarioResolve from './route/dependentes-funcionario-routing-resolve.service';

const dependentesFuncionarioRoute: Routes = [
  {
    path: '',
    component: DependentesFuncionarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DependentesFuncionarioDetailComponent,
    resolve: {
      dependentesFuncionario: DependentesFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DependentesFuncionarioUpdateComponent,
    resolve: {
      dependentesFuncionario: DependentesFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DependentesFuncionarioUpdateComponent,
    resolve: {
      dependentesFuncionario: DependentesFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dependentesFuncionarioRoute;
