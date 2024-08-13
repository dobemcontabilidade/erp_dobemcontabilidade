import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GatewayPagamentoComponent } from './list/gateway-pagamento.component';
import { GatewayPagamentoDetailComponent } from './detail/gateway-pagamento-detail.component';
import { GatewayPagamentoUpdateComponent } from './update/gateway-pagamento-update.component';
import GatewayPagamentoResolve from './route/gateway-pagamento-routing-resolve.service';

const gatewayPagamentoRoute: Routes = [
  {
    path: '',
    component: GatewayPagamentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GatewayPagamentoDetailComponent,
    resolve: {
      gatewayPagamento: GatewayPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GatewayPagamentoUpdateComponent,
    resolve: {
      gatewayPagamento: GatewayPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GatewayPagamentoUpdateComponent,
    resolve: {
      gatewayPagamento: GatewayPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default gatewayPagamentoRoute;
