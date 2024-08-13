import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaComponent } from './list/tarefa.component';
import { TarefaDetailComponent } from './detail/tarefa-detail.component';
import { TarefaUpdateComponent } from './update/tarefa-update.component';
import TarefaResolve from './route/tarefa-routing-resolve.service';

const tarefaRoute: Routes = [
  {
    path: '',
    component: TarefaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaDetailComponent,
    resolve: {
      tarefa: TarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaUpdateComponent,
    resolve: {
      tarefa: TarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaUpdateComponent,
    resolve: {
      tarefa: TarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaRoute;
