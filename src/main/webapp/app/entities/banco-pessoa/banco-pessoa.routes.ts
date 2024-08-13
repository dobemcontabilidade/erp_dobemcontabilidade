import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BancoPessoaComponent } from './list/banco-pessoa.component';
import { BancoPessoaDetailComponent } from './detail/banco-pessoa-detail.component';
import { BancoPessoaUpdateComponent } from './update/banco-pessoa-update.component';
import BancoPessoaResolve from './route/banco-pessoa-routing-resolve.service';

const bancoPessoaRoute: Routes = [
  {
    path: '',
    component: BancoPessoaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BancoPessoaDetailComponent,
    resolve: {
      bancoPessoa: BancoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BancoPessoaUpdateComponent,
    resolve: {
      bancoPessoa: BancoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BancoPessoaUpdateComponent,
    resolve: {
      bancoPessoa: BancoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bancoPessoaRoute;
