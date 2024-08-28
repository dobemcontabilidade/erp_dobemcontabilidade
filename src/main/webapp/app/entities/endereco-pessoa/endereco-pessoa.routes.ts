import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EnderecoPessoaComponent } from './list/endereco-pessoa.component';
import { EnderecoPessoaDetailComponent } from './detail/endereco-pessoa-detail.component';
import { EnderecoPessoaUpdateComponent } from './update/endereco-pessoa-update.component';
import EnderecoPessoaResolve from './route/endereco-pessoa-routing-resolve.service';

const enderecoPessoaRoute: Routes = [
  {
    path: '',
    component: EnderecoPessoaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnderecoPessoaDetailComponent,
    resolve: {
      enderecoPessoa: EnderecoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnderecoPessoaUpdateComponent,
    resolve: {
      enderecoPessoa: EnderecoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnderecoPessoaUpdateComponent,
    resolve: {
      enderecoPessoa: EnderecoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default enderecoPessoaRoute;
