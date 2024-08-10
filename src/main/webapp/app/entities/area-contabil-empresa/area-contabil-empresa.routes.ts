import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AreaContabilEmpresaComponent } from './list/area-contabil-empresa.component';
import { AreaContabilEmpresaDetailComponent } from './detail/area-contabil-empresa-detail.component';
import { AreaContabilEmpresaUpdateComponent } from './update/area-contabil-empresa-update.component';
import AreaContabilEmpresaResolve from './route/area-contabil-empresa-routing-resolve.service';

const areaContabilEmpresaRoute: Routes = [
  {
    path: '',
    component: AreaContabilEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AreaContabilEmpresaDetailComponent,
    resolve: {
      areaContabilEmpresa: AreaContabilEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaContabilEmpresaUpdateComponent,
    resolve: {
      areaContabilEmpresa: AreaContabilEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AreaContabilEmpresaUpdateComponent,
    resolve: {
      areaContabilEmpresa: AreaContabilEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default areaContabilEmpresaRoute;
