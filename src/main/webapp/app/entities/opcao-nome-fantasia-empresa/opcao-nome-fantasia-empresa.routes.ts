import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { OpcaoNomeFantasiaEmpresaComponent } from './list/opcao-nome-fantasia-empresa.component';
import { OpcaoNomeFantasiaEmpresaDetailComponent } from './detail/opcao-nome-fantasia-empresa-detail.component';
import { OpcaoNomeFantasiaEmpresaUpdateComponent } from './update/opcao-nome-fantasia-empresa-update.component';
import OpcaoNomeFantasiaEmpresaResolve from './route/opcao-nome-fantasia-empresa-routing-resolve.service';

const opcaoNomeFantasiaEmpresaRoute: Routes = [
  {
    path: '',
    component: OpcaoNomeFantasiaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OpcaoNomeFantasiaEmpresaDetailComponent,
    resolve: {
      opcaoNomeFantasiaEmpresa: OpcaoNomeFantasiaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OpcaoNomeFantasiaEmpresaUpdateComponent,
    resolve: {
      opcaoNomeFantasiaEmpresa: OpcaoNomeFantasiaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OpcaoNomeFantasiaEmpresaUpdateComponent,
    resolve: {
      opcaoNomeFantasiaEmpresa: OpcaoNomeFantasiaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default opcaoNomeFantasiaEmpresaRoute;
