import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PeriodoPagamentoComponent } from './list/periodo-pagamento.component';
import { PeriodoPagamentoDetailComponent } from './detail/periodo-pagamento-detail.component';
import { PeriodoPagamentoUpdateComponent } from './update/periodo-pagamento-update.component';
import PeriodoPagamentoResolve from './route/periodo-pagamento-routing-resolve.service';

const periodoPagamentoRoute: Routes = [
  {
    path: '',
    component: PeriodoPagamentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeriodoPagamentoDetailComponent,
    resolve: {
      periodoPagamento: PeriodoPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeriodoPagamentoUpdateComponent,
    resolve: {
      periodoPagamento: PeriodoPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeriodoPagamentoUpdateComponent,
    resolve: {
      periodoPagamento: PeriodoPagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default periodoPagamentoRoute;
