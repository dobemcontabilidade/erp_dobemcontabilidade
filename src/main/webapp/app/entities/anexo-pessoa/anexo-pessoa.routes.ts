import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoPessoaComponent } from './list/anexo-pessoa.component';
import { AnexoPessoaDetailComponent } from './detail/anexo-pessoa-detail.component';
import { AnexoPessoaUpdateComponent } from './update/anexo-pessoa-update.component';
import AnexoPessoaResolve from './route/anexo-pessoa-routing-resolve.service';

const anexoPessoaRoute: Routes = [
  {
    path: '',
    component: AnexoPessoaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoPessoaDetailComponent,
    resolve: {
      anexoPessoa: AnexoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoPessoaUpdateComponent,
    resolve: {
      anexoPessoa: AnexoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoPessoaUpdateComponent,
    resolve: {
      anexoPessoa: AnexoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoPessoaRoute;
