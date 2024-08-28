import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DocsPessoaComponent } from './list/docs-pessoa.component';
import { DocsPessoaDetailComponent } from './detail/docs-pessoa-detail.component';
import { DocsPessoaUpdateComponent } from './update/docs-pessoa-update.component';
import DocsPessoaResolve from './route/docs-pessoa-routing-resolve.service';

const docsPessoaRoute: Routes = [
  {
    path: '',
    component: DocsPessoaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocsPessoaDetailComponent,
    resolve: {
      docsPessoa: DocsPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocsPessoaUpdateComponent,
    resolve: {
      docsPessoa: DocsPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocsPessoaUpdateComponent,
    resolve: {
      docsPessoa: DocsPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default docsPessoaRoute;
