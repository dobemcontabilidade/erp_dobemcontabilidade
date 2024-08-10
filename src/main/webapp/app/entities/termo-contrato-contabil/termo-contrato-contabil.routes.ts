import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TermoContratoContabilComponent } from './list/termo-contrato-contabil.component';
import { TermoContratoContabilDetailComponent } from './detail/termo-contrato-contabil-detail.component';
import { TermoContratoContabilUpdateComponent } from './update/termo-contrato-contabil-update.component';
import TermoContratoContabilResolve from './route/termo-contrato-contabil-routing-resolve.service';

const termoContratoContabilRoute: Routes = [
  {
    path: '',
    component: TermoContratoContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TermoContratoContabilDetailComponent,
    resolve: {
      termoContratoContabil: TermoContratoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TermoContratoContabilUpdateComponent,
    resolve: {
      termoContratoContabil: TermoContratoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TermoContratoContabilUpdateComponent,
    resolve: {
      termoContratoContabil: TermoContratoContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default termoContratoContabilRoute;
