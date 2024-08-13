import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CobrancaEmpresaComponent } from './list/cobranca-empresa.component';
import { CobrancaEmpresaDetailComponent } from './detail/cobranca-empresa-detail.component';
import { CobrancaEmpresaUpdateComponent } from './update/cobranca-empresa-update.component';
import CobrancaEmpresaResolve from './route/cobranca-empresa-routing-resolve.service';

const cobrancaEmpresaRoute: Routes = [
  {
    path: '',
    component: CobrancaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CobrancaEmpresaDetailComponent,
    resolve: {
      cobrancaEmpresa: CobrancaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CobrancaEmpresaUpdateComponent,
    resolve: {
      cobrancaEmpresa: CobrancaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CobrancaEmpresaUpdateComponent,
    resolve: {
      cobrancaEmpresa: CobrancaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cobrancaEmpresaRoute;
