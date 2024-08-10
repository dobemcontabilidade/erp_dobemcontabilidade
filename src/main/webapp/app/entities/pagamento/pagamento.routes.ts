import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PagamentoComponent } from './list/pagamento.component';
import { PagamentoDetailComponent } from './detail/pagamento-detail.component';
import { PagamentoUpdateComponent } from './update/pagamento-update.component';
import PagamentoResolve from './route/pagamento-routing-resolve.service';

const pagamentoRoute: Routes = [
  {
    path: '',
    component: PagamentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PagamentoDetailComponent,
    resolve: {
      pagamento: PagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PagamentoUpdateComponent,
    resolve: {
      pagamento: PagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PagamentoUpdateComponent,
    resolve: {
      pagamento: PagamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pagamentoRoute;
