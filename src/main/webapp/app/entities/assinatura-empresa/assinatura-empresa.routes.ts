import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AssinaturaEmpresaComponent } from './list/assinatura-empresa.component';
import { AssinaturaEmpresaDetailComponent } from './detail/assinatura-empresa-detail.component';
import { AssinaturaEmpresaUpdateComponent } from './update/assinatura-empresa-update.component';
import AssinaturaEmpresaResolve from './route/assinatura-empresa-routing-resolve.service';

const assinaturaEmpresaRoute: Routes = [
  {
    path: '',
    component: AssinaturaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssinaturaEmpresaDetailComponent,
    resolve: {
      assinaturaEmpresa: AssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssinaturaEmpresaUpdateComponent,
    resolve: {
      assinaturaEmpresa: AssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssinaturaEmpresaUpdateComponent,
    resolve: {
      assinaturaEmpresa: AssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default assinaturaEmpresaRoute;
