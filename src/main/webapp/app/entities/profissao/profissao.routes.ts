import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProfissaoComponent } from './list/profissao.component';
import { ProfissaoDetailComponent } from './detail/profissao-detail.component';
import { ProfissaoUpdateComponent } from './update/profissao-update.component';
import ProfissaoResolve from './route/profissao-routing-resolve.service';

const profissaoRoute: Routes = [
  {
    path: '',
    component: ProfissaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProfissaoDetailComponent,
    resolve: {
      profissao: ProfissaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProfissaoUpdateComponent,
    resolve: {
      profissao: ProfissaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProfissaoUpdateComponent,
    resolve: {
      profissao: ProfissaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default profissaoRoute;
