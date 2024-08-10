import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoEmpresaComponent } from './list/anexo-empresa.component';
import { AnexoEmpresaDetailComponent } from './detail/anexo-empresa-detail.component';
import { AnexoEmpresaUpdateComponent } from './update/anexo-empresa-update.component';
import AnexoEmpresaResolve from './route/anexo-empresa-routing-resolve.service';

const anexoEmpresaRoute: Routes = [
  {
    path: '',
    component: AnexoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoEmpresaDetailComponent,
    resolve: {
      anexoEmpresa: AnexoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoEmpresaUpdateComponent,
    resolve: {
      anexoEmpresa: AnexoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoEmpresaUpdateComponent,
    resolve: {
      anexoEmpresa: AnexoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoEmpresaRoute;
