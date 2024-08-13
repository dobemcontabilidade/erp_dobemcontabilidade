import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ImpostoParceladoComponent } from './list/imposto-parcelado.component';
import { ImpostoParceladoDetailComponent } from './detail/imposto-parcelado-detail.component';
import { ImpostoParceladoUpdateComponent } from './update/imposto-parcelado-update.component';
import ImpostoParceladoResolve from './route/imposto-parcelado-routing-resolve.service';

const impostoParceladoRoute: Routes = [
  {
    path: '',
    component: ImpostoParceladoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImpostoParceladoDetailComponent,
    resolve: {
      impostoParcelado: ImpostoParceladoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImpostoParceladoUpdateComponent,
    resolve: {
      impostoParcelado: ImpostoParceladoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImpostoParceladoUpdateComponent,
    resolve: {
      impostoParcelado: ImpostoParceladoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default impostoParceladoRoute;
