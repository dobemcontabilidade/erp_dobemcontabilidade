import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoRequeridoTarefaRecorrenteComponent } from './list/anexo-requerido-tarefa-recorrente.component';
import { AnexoRequeridoTarefaRecorrenteDetailComponent } from './detail/anexo-requerido-tarefa-recorrente-detail.component';
import { AnexoRequeridoTarefaRecorrenteUpdateComponent } from './update/anexo-requerido-tarefa-recorrente-update.component';
import AnexoRequeridoTarefaRecorrenteResolve from './route/anexo-requerido-tarefa-recorrente-routing-resolve.service';

const anexoRequeridoTarefaRecorrenteRoute: Routes = [
  {
    path: '',
    component: AnexoRequeridoTarefaRecorrenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoRequeridoTarefaRecorrenteDetailComponent,
    resolve: {
      anexoRequeridoTarefaRecorrente: AnexoRequeridoTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoRequeridoTarefaRecorrenteUpdateComponent,
    resolve: {
      anexoRequeridoTarefaRecorrente: AnexoRequeridoTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoRequeridoTarefaRecorrenteUpdateComponent,
    resolve: {
      anexoRequeridoTarefaRecorrente: AnexoRequeridoTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoRequeridoTarefaRecorrenteRoute;
