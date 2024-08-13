import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TipoDenunciaComponent } from './list/tipo-denuncia.component';
import { TipoDenunciaDetailComponent } from './detail/tipo-denuncia-detail.component';
import { TipoDenunciaUpdateComponent } from './update/tipo-denuncia-update.component';
import TipoDenunciaResolve from './route/tipo-denuncia-routing-resolve.service';

const tipoDenunciaRoute: Routes = [
  {
    path: '',
    component: TipoDenunciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoDenunciaDetailComponent,
    resolve: {
      tipoDenuncia: TipoDenunciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoDenunciaUpdateComponent,
    resolve: {
      tipoDenuncia: TipoDenunciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoDenunciaUpdateComponent,
    resolve: {
      tipoDenuncia: TipoDenunciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tipoDenunciaRoute;
