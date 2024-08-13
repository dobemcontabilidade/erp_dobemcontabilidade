import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ParcelamentoImpostoComponent } from './list/parcelamento-imposto.component';
import { ParcelamentoImpostoDetailComponent } from './detail/parcelamento-imposto-detail.component';
import { ParcelamentoImpostoUpdateComponent } from './update/parcelamento-imposto-update.component';
import ParcelamentoImpostoResolve from './route/parcelamento-imposto-routing-resolve.service';

const parcelamentoImpostoRoute: Routes = [
  {
    path: '',
    component: ParcelamentoImpostoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParcelamentoImpostoDetailComponent,
    resolve: {
      parcelamentoImposto: ParcelamentoImpostoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParcelamentoImpostoUpdateComponent,
    resolve: {
      parcelamentoImposto: ParcelamentoImpostoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParcelamentoImpostoUpdateComponent,
    resolve: {
      parcelamentoImposto: ParcelamentoImpostoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default parcelamentoImpostoRoute;
