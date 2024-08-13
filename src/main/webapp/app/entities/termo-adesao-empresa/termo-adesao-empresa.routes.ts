import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TermoAdesaoEmpresaComponent } from './list/termo-adesao-empresa.component';
import { TermoAdesaoEmpresaDetailComponent } from './detail/termo-adesao-empresa-detail.component';
import { TermoAdesaoEmpresaUpdateComponent } from './update/termo-adesao-empresa-update.component';
import TermoAdesaoEmpresaResolve from './route/termo-adesao-empresa-routing-resolve.service';

const termoAdesaoEmpresaRoute: Routes = [
  {
    path: '',
    component: TermoAdesaoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TermoAdesaoEmpresaDetailComponent,
    resolve: {
      termoAdesaoEmpresa: TermoAdesaoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TermoAdesaoEmpresaUpdateComponent,
    resolve: {
      termoAdesaoEmpresa: TermoAdesaoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TermoAdesaoEmpresaUpdateComponent,
    resolve: {
      termoAdesaoEmpresa: TermoAdesaoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default termoAdesaoEmpresaRoute;
