import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ImpostoAPagarEmpresaComponent } from './list/imposto-a-pagar-empresa.component';
import { ImpostoAPagarEmpresaDetailComponent } from './detail/imposto-a-pagar-empresa-detail.component';
import { ImpostoAPagarEmpresaUpdateComponent } from './update/imposto-a-pagar-empresa-update.component';
import ImpostoAPagarEmpresaResolve from './route/imposto-a-pagar-empresa-routing-resolve.service';

const impostoAPagarEmpresaRoute: Routes = [
  {
    path: '',
    component: ImpostoAPagarEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImpostoAPagarEmpresaDetailComponent,
    resolve: {
      impostoAPagarEmpresa: ImpostoAPagarEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImpostoAPagarEmpresaUpdateComponent,
    resolve: {
      impostoAPagarEmpresa: ImpostoAPagarEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImpostoAPagarEmpresaUpdateComponent,
    resolve: {
      impostoAPagarEmpresa: ImpostoAPagarEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default impostoAPagarEmpresaRoute;
