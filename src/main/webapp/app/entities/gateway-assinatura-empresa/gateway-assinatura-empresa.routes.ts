import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GatewayAssinaturaEmpresaComponent } from './list/gateway-assinatura-empresa.component';
import { GatewayAssinaturaEmpresaDetailComponent } from './detail/gateway-assinatura-empresa-detail.component';
import { GatewayAssinaturaEmpresaUpdateComponent } from './update/gateway-assinatura-empresa-update.component';
import GatewayAssinaturaEmpresaResolve from './route/gateway-assinatura-empresa-routing-resolve.service';

const gatewayAssinaturaEmpresaRoute: Routes = [
  {
    path: '',
    component: GatewayAssinaturaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GatewayAssinaturaEmpresaDetailComponent,
    resolve: {
      gatewayAssinaturaEmpresa: GatewayAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GatewayAssinaturaEmpresaUpdateComponent,
    resolve: {
      gatewayAssinaturaEmpresa: GatewayAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GatewayAssinaturaEmpresaUpdateComponent,
    resolve: {
      gatewayAssinaturaEmpresa: GatewayAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default gatewayAssinaturaEmpresaRoute;
