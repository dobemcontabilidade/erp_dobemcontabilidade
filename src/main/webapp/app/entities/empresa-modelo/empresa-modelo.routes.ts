import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EmpresaModeloComponent } from './list/empresa-modelo.component';
import { EmpresaModeloDetailComponent } from './detail/empresa-modelo-detail.component';
import { EmpresaModeloUpdateComponent } from './update/empresa-modelo-update.component';
import EmpresaModeloResolve from './route/empresa-modelo-routing-resolve.service';

const empresaModeloRoute: Routes = [
  {
    path: '',
    component: EmpresaModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmpresaModeloDetailComponent,
    resolve: {
      empresaModelo: EmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmpresaModeloUpdateComponent,
    resolve: {
      empresaModelo: EmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmpresaModeloUpdateComponent,
    resolve: {
      empresaModelo: EmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default empresaModeloRoute;
