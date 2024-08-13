import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AgendaContadorConfigComponent } from './list/agenda-contador-config.component';
import { AgendaContadorConfigDetailComponent } from './detail/agenda-contador-config-detail.component';
import { AgendaContadorConfigUpdateComponent } from './update/agenda-contador-config-update.component';
import AgendaContadorConfigResolve from './route/agenda-contador-config-routing-resolve.service';

const agendaContadorConfigRoute: Routes = [
  {
    path: '',
    component: AgendaContadorConfigComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgendaContadorConfigDetailComponent,
    resolve: {
      agendaContadorConfig: AgendaContadorConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgendaContadorConfigUpdateComponent,
    resolve: {
      agendaContadorConfig: AgendaContadorConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgendaContadorConfigUpdateComponent,
    resolve: {
      agendaContadorConfig: AgendaContadorConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default agendaContadorConfigRoute;
