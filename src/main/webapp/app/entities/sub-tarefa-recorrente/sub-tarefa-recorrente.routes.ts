import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SubTarefaRecorrenteComponent } from './list/sub-tarefa-recorrente.component';
import { SubTarefaRecorrenteDetailComponent } from './detail/sub-tarefa-recorrente-detail.component';
import { SubTarefaRecorrenteUpdateComponent } from './update/sub-tarefa-recorrente-update.component';
import SubTarefaRecorrenteResolve from './route/sub-tarefa-recorrente-routing-resolve.service';

const subTarefaRecorrenteRoute: Routes = [
  {
    path: '',
    component: SubTarefaRecorrenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubTarefaRecorrenteDetailComponent,
    resolve: {
      subTarefaRecorrente: SubTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubTarefaRecorrenteUpdateComponent,
    resolve: {
      subTarefaRecorrente: SubTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubTarefaRecorrenteUpdateComponent,
    resolve: {
      subTarefaRecorrente: SubTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subTarefaRecorrenteRoute;
