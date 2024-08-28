import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DescontoPeriodoPagamentoComponent } from './list/desconto-periodo-pagamento.component';
import { DescontoPeriodoPagamentoDetailComponent } from './detail/desconto-periodo-pagamento-detail.component';
import { DescontoPeriodoPagamentoUpdateComponent } from './update/desconto-periodo-pagamento-update.component';
import DescontoPeriodoPagamentoResolve from './route/desconto-periodo-pagamento-routing-resolve.service';

const descontoPeriodoPagamentoRoute: Routes = [
  {
    path: '',
    component: DescontoPeriodoPagamentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DescontoPeriodoPagamentoDetailComponent,
    resolve: {
      descontoPeriodoPagamento: DescontoPeriodoPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DescontoPeriodoPagamentoUpdateComponent,
    resolve: {
      descontoPeriodoPagamento: DescontoPeriodoPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DescontoPeriodoPagamentoUpdateComponent,
    resolve: {
      descontoPeriodoPagamento: DescontoPeriodoPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default descontoPeriodoPagamentoRoute;
