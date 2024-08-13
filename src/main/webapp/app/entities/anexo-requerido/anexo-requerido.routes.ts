import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoRequeridoComponent } from './list/anexo-requerido.component';
import { AnexoRequeridoDetailComponent } from './detail/anexo-requerido-detail.component';
import { AnexoRequeridoUpdateComponent } from './update/anexo-requerido-update.component';
import AnexoRequeridoResolve from './route/anexo-requerido-routing-resolve.service';

const anexoRequeridoRoute: Routes = [
  {
    path: '',
    component: AnexoRequeridoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoRequeridoDetailComponent,
    resolve: {
      anexoRequerido: AnexoRequeridoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoRequeridoUpdateComponent,
    resolve: {
      anexoRequerido: AnexoRequeridoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoRequeridoUpdateComponent,
    resolve: {
      anexoRequerido: AnexoRequeridoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoRequeridoRoute;
