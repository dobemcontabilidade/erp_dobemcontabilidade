import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PerfilContadorAreaContabilComponent } from './list/perfil-contador-area-contabil.component';
import { PerfilContadorAreaContabilDetailComponent } from './detail/perfil-contador-area-contabil-detail.component';
import { PerfilContadorAreaContabilUpdateComponent } from './update/perfil-contador-area-contabil-update.component';
import PerfilContadorAreaContabilResolve from './route/perfil-contador-area-contabil-routing-resolve.service';

const perfilContadorAreaContabilRoute: Routes = [
  {
    path: '',
    component: PerfilContadorAreaContabilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfilContadorAreaContabilDetailComponent,
    resolve: {
      perfilContadorAreaContabil: PerfilContadorAreaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfilContadorAreaContabilUpdateComponent,
    resolve: {
      perfilContadorAreaContabil: PerfilContadorAreaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfilContadorAreaContabilUpdateComponent,
    resolve: {
      perfilContadorAreaContabil: PerfilContadorAreaContabilResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default perfilContadorAreaContabilRoute;
