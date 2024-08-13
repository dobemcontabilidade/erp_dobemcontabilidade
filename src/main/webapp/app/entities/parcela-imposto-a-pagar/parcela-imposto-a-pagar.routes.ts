import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ParcelaImpostoAPagarComponent } from './list/parcela-imposto-a-pagar.component';
import { ParcelaImpostoAPagarDetailComponent } from './detail/parcela-imposto-a-pagar-detail.component';
import { ParcelaImpostoAPagarUpdateComponent } from './update/parcela-imposto-a-pagar-update.component';
import ParcelaImpostoAPagarResolve from './route/parcela-imposto-a-pagar-routing-resolve.service';

const parcelaImpostoAPagarRoute: Routes = [
  {
    path: '',
    component: ParcelaImpostoAPagarComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParcelaImpostoAPagarDetailComponent,
    resolve: {
      parcelaImpostoAPagar: ParcelaImpostoAPagarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParcelaImpostoAPagarUpdateComponent,
    resolve: {
      parcelaImpostoAPagar: ParcelaImpostoAPagarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParcelaImpostoAPagarUpdateComponent,
    resolve: {
      parcelaImpostoAPagar: ParcelaImpostoAPagarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default parcelaImpostoAPagarRoute;
