import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoRequeridoPessoaComponent } from './list/anexo-requerido-pessoa.component';
import { AnexoRequeridoPessoaDetailComponent } from './detail/anexo-requerido-pessoa-detail.component';
import { AnexoRequeridoPessoaUpdateComponent } from './update/anexo-requerido-pessoa-update.component';
import AnexoRequeridoPessoaResolve from './route/anexo-requerido-pessoa-routing-resolve.service';

const anexoRequeridoPessoaRoute: Routes = [
  {
    path: '',
    component: AnexoRequeridoPessoaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoRequeridoPessoaDetailComponent,
    resolve: {
      anexoRequeridoPessoa: AnexoRequeridoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoRequeridoPessoaUpdateComponent,
    resolve: {
      anexoRequeridoPessoa: AnexoRequeridoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoRequeridoPessoaUpdateComponent,
    resolve: {
      anexoRequeridoPessoa: AnexoRequeridoPessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoRequeridoPessoaRoute;
