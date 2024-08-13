import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AreaContabilAssinaturaEmpresaComponent } from './list/area-contabil-assinatura-empresa.component';
import { AreaContabilAssinaturaEmpresaDetailComponent } from './detail/area-contabil-assinatura-empresa-detail.component';
import { AreaContabilAssinaturaEmpresaUpdateComponent } from './update/area-contabil-assinatura-empresa-update.component';
import AreaContabilAssinaturaEmpresaResolve from './route/area-contabil-assinatura-empresa-routing-resolve.service';

const areaContabilAssinaturaEmpresaRoute: Routes = [
  {
    path: '',
    component: AreaContabilAssinaturaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AreaContabilAssinaturaEmpresaDetailComponent,
    resolve: {
      areaContabilAssinaturaEmpresa: AreaContabilAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaContabilAssinaturaEmpresaUpdateComponent,
    resolve: {
      areaContabilAssinaturaEmpresa: AreaContabilAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AreaContabilAssinaturaEmpresaUpdateComponent,
    resolve: {
      areaContabilAssinaturaEmpresa: AreaContabilAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default areaContabilAssinaturaEmpresaRoute;
