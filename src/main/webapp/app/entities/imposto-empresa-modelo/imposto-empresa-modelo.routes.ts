import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ImpostoEmpresaModeloComponent } from './list/imposto-empresa-modelo.component';
import { ImpostoEmpresaModeloDetailComponent } from './detail/imposto-empresa-modelo-detail.component';
import { ImpostoEmpresaModeloUpdateComponent } from './update/imposto-empresa-modelo-update.component';
import ImpostoEmpresaModeloResolve from './route/imposto-empresa-modelo-routing-resolve.service';

const impostoEmpresaModeloRoute: Routes = [
  {
    path: '',
    component: ImpostoEmpresaModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImpostoEmpresaModeloDetailComponent,
    resolve: {
      impostoEmpresaModelo: ImpostoEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImpostoEmpresaModeloUpdateComponent,
    resolve: {
      impostoEmpresaModelo: ImpostoEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImpostoEmpresaModeloUpdateComponent,
    resolve: {
      impostoEmpresaModelo: ImpostoEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default impostoEmpresaModeloRoute;
