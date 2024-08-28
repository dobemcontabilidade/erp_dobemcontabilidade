import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FormaDePagamentoComponent } from './list/forma-de-pagamento.component';
import { FormaDePagamentoDetailComponent } from './detail/forma-de-pagamento-detail.component';
import { FormaDePagamentoUpdateComponent } from './update/forma-de-pagamento-update.component';
import FormaDePagamentoResolve from './route/forma-de-pagamento-routing-resolve.service';

const formaDePagamentoRoute: Routes = [
  {
    path: '',
    component: FormaDePagamentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormaDePagamentoDetailComponent,
    resolve: {
      formaDePagamento: FormaDePagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormaDePagamentoUpdateComponent,
    resolve: {
      formaDePagamento: FormaDePagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormaDePagamentoUpdateComponent,
    resolve: {
      formaDePagamento: FormaDePagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default formaDePagamentoRoute;
