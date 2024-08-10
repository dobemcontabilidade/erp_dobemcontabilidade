import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { OpcaoRazaoSocialEmpresaComponent } from './list/opcao-razao-social-empresa.component';
import { OpcaoRazaoSocialEmpresaDetailComponent } from './detail/opcao-razao-social-empresa-detail.component';
import { OpcaoRazaoSocialEmpresaUpdateComponent } from './update/opcao-razao-social-empresa-update.component';
import OpcaoRazaoSocialEmpresaResolve from './route/opcao-razao-social-empresa-routing-resolve.service';

const opcaoRazaoSocialEmpresaRoute: Routes = [
  {
    path: '',
    component: OpcaoRazaoSocialEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OpcaoRazaoSocialEmpresaDetailComponent,
    resolve: {
      opcaoRazaoSocialEmpresa: OpcaoRazaoSocialEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OpcaoRazaoSocialEmpresaUpdateComponent,
    resolve: {
      opcaoRazaoSocialEmpresa: OpcaoRazaoSocialEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OpcaoRazaoSocialEmpresaUpdateComponent,
    resolve: {
      opcaoRazaoSocialEmpresa: OpcaoRazaoSocialEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default opcaoRazaoSocialEmpresaRoute;
