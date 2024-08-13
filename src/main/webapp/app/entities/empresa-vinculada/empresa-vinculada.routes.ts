import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EmpresaVinculadaComponent } from './list/empresa-vinculada.component';
import { EmpresaVinculadaDetailComponent } from './detail/empresa-vinculada-detail.component';
import { EmpresaVinculadaUpdateComponent } from './update/empresa-vinculada-update.component';
import EmpresaVinculadaResolve from './route/empresa-vinculada-routing-resolve.service';

const empresaVinculadaRoute: Routes = [
  {
    path: '',
    component: EmpresaVinculadaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmpresaVinculadaDetailComponent,
    resolve: {
      empresaVinculada: EmpresaVinculadaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmpresaVinculadaUpdateComponent,
    resolve: {
      empresaVinculada: EmpresaVinculadaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmpresaVinculadaUpdateComponent,
    resolve: {
      empresaVinculada: EmpresaVinculadaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default empresaVinculadaRoute;
