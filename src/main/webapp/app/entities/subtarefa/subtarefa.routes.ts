import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SubtarefaComponent } from './list/subtarefa.component';
import { SubtarefaDetailComponent } from './detail/subtarefa-detail.component';
import { SubtarefaUpdateComponent } from './update/subtarefa-update.component';
import SubtarefaResolve from './route/subtarefa-routing-resolve.service';

const subtarefaRoute: Routes = [
  {
    path: '',
    component: SubtarefaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubtarefaDetailComponent,
    resolve: {
      subtarefa: SubtarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubtarefaUpdateComponent,
    resolve: {
      subtarefa: SubtarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubtarefaUpdateComponent,
    resolve: {
      subtarefa: SubtarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subtarefaRoute;
