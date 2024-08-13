import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoRequeridoEmpresaComponent } from './list/anexo-requerido-empresa.component';
import { AnexoRequeridoEmpresaDetailComponent } from './detail/anexo-requerido-empresa-detail.component';
import { AnexoRequeridoEmpresaUpdateComponent } from './update/anexo-requerido-empresa-update.component';
import AnexoRequeridoEmpresaResolve from './route/anexo-requerido-empresa-routing-resolve.service';

const anexoRequeridoEmpresaRoute: Routes = [
  {
    path: '',
    component: AnexoRequeridoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoRequeridoEmpresaDetailComponent,
    resolve: {
      anexoRequeridoEmpresa: AnexoRequeridoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoRequeridoEmpresaUpdateComponent,
    resolve: {
      anexoRequeridoEmpresa: AnexoRequeridoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoRequeridoEmpresaUpdateComponent,
    resolve: {
      anexoRequeridoEmpresa: AnexoRequeridoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoRequeridoEmpresaRoute;
