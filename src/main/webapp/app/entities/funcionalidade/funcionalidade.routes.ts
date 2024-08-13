import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FuncionalidadeComponent } from './list/funcionalidade.component';
import { FuncionalidadeDetailComponent } from './detail/funcionalidade-detail.component';
import { FuncionalidadeUpdateComponent } from './update/funcionalidade-update.component';
import FuncionalidadeResolve from './route/funcionalidade-routing-resolve.service';

const funcionalidadeRoute: Routes = [
  {
    path: '',
    component: FuncionalidadeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuncionalidadeDetailComponent,
    resolve: {
      funcionalidade: FuncionalidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuncionalidadeUpdateComponent,
    resolve: {
      funcionalidade: FuncionalidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuncionalidadeUpdateComponent,
    resolve: {
      funcionalidade: FuncionalidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default funcionalidadeRoute;
