import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContadorResponsavelTarefaRecorrenteComponent } from './list/contador-responsavel-tarefa-recorrente.component';
import { ContadorResponsavelTarefaRecorrenteDetailComponent } from './detail/contador-responsavel-tarefa-recorrente-detail.component';
import { ContadorResponsavelTarefaRecorrenteUpdateComponent } from './update/contador-responsavel-tarefa-recorrente-update.component';
import ContadorResponsavelTarefaRecorrenteResolve from './route/contador-responsavel-tarefa-recorrente-routing-resolve.service';

const contadorResponsavelTarefaRecorrenteRoute: Routes = [
  {
    path: '',
    component: ContadorResponsavelTarefaRecorrenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContadorResponsavelTarefaRecorrenteDetailComponent,
    resolve: {
      contadorResponsavelTarefaRecorrente: ContadorResponsavelTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContadorResponsavelTarefaRecorrenteUpdateComponent,
    resolve: {
      contadorResponsavelTarefaRecorrente: ContadorResponsavelTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContadorResponsavelTarefaRecorrenteUpdateComponent,
    resolve: {
      contadorResponsavelTarefaRecorrente: ContadorResponsavelTarefaRecorrenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contadorResponsavelTarefaRecorrenteRoute;
