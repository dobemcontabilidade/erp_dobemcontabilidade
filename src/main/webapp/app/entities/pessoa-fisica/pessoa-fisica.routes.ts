import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PessoaFisicaComponent } from './list/pessoa-fisica.component';
import { PessoaFisicaDetailComponent } from './detail/pessoa-fisica-detail.component';
import { PessoaFisicaUpdateComponent } from './update/pessoa-fisica-update.component';
import PessoaFisicaResolve from './route/pessoa-fisica-routing-resolve.service';

const pessoaFisicaRoute: Routes = [
  {
    path: '',
    component: PessoaFisicaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PessoaFisicaDetailComponent,
    resolve: {
      pessoaFisica: PessoaFisicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PessoaFisicaUpdateComponent,
    resolve: {
      pessoaFisica: PessoaFisicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PessoaFisicaUpdateComponent,
    resolve: {
      pessoaFisica: PessoaFisicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pessoaFisicaRoute;
