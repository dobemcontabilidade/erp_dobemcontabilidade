import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ImpostoEmpresaComponent } from './list/imposto-empresa.component';
import { ImpostoEmpresaDetailComponent } from './detail/imposto-empresa-detail.component';
import { ImpostoEmpresaUpdateComponent } from './update/imposto-empresa-update.component';
import ImpostoEmpresaResolve from './route/imposto-empresa-routing-resolve.service';

const impostoEmpresaRoute: Routes = [
  {
    path: '',
    component: ImpostoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImpostoEmpresaDetailComponent,
    resolve: {
      impostoEmpresa: ImpostoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImpostoEmpresaUpdateComponent,
    resolve: {
      impostoEmpresa: ImpostoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImpostoEmpresaUpdateComponent,
    resolve: {
      impostoEmpresa: ImpostoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default impostoEmpresaRoute;
