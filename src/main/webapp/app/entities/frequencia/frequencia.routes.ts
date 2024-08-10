import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FrequenciaComponent } from './list/frequencia.component';
import { FrequenciaDetailComponent } from './detail/frequencia-detail.component';
import { FrequenciaUpdateComponent } from './update/frequencia-update.component';
import FrequenciaResolve from './route/frequencia-routing-resolve.service';

const frequenciaRoute: Routes = [
  {
    path: '',
    component: FrequenciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FrequenciaDetailComponent,
    resolve: {
      frequencia: FrequenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FrequenciaUpdateComponent,
    resolve: {
      frequencia: FrequenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FrequenciaUpdateComponent,
    resolve: {
      frequencia: FrequenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default frequenciaRoute;
