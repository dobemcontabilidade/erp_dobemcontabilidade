import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CompetenciaComponent } from './list/competencia.component';
import { CompetenciaDetailComponent } from './detail/competencia-detail.component';
import { CompetenciaUpdateComponent } from './update/competencia-update.component';
import CompetenciaResolve from './route/competencia-routing-resolve.service';

const competenciaRoute: Routes = [
  {
    path: '',
    component: CompetenciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompetenciaDetailComponent,
    resolve: {
      competencia: CompetenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompetenciaUpdateComponent,
    resolve: {
      competencia: CompetenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompetenciaUpdateComponent,
    resolve: {
      competencia: CompetenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default competenciaRoute;
