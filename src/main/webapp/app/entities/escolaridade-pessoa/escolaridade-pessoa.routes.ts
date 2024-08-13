import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EscolaridadePessoaComponent } from './list/escolaridade-pessoa.component';
import { EscolaridadePessoaDetailComponent } from './detail/escolaridade-pessoa-detail.component';
import { EscolaridadePessoaUpdateComponent } from './update/escolaridade-pessoa-update.component';
import EscolaridadePessoaResolve from './route/escolaridade-pessoa-routing-resolve.service';

const escolaridadePessoaRoute: Routes = [
  {
    path: '',
    component: EscolaridadePessoaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EscolaridadePessoaDetailComponent,
    resolve: {
      escolaridadePessoa: EscolaridadePessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EscolaridadePessoaUpdateComponent,
    resolve: {
      escolaridadePessoa: EscolaridadePessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EscolaridadePessoaUpdateComponent,
    resolve: {
      escolaridadePessoa: EscolaridadePessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default escolaridadePessoaRoute;
