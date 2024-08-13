import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaRecorrenteComponent } from './list/tarefa-recorrente.component';
import { TarefaRecorrenteDetailComponent } from './detail/tarefa-recorrente-detail.component';
import { TarefaRecorrenteUpdateComponent } from './update/tarefa-recorrente-update.component';
import TarefaRecorrenteResolve from './route/tarefa-recorrente-routing-resolve.service';

const tarefaRecorrenteRoute: Routes = [
  {
    path: '',
    component: TarefaRecorrenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaRecorrenteDetailComponent,
    resolve: {
      tarefaRecorrente: TarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaRecorrenteUpdateComponent,
    resolve: {
      tarefaRecorrente: TarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaRecorrenteUpdateComponent,
    resolve: {
      tarefaRecorrente: TarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaRecorrenteRoute;
