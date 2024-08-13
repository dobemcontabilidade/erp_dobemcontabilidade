import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaRecorrenteEmpresaModeloComponent } from './list/tarefa-recorrente-empresa-modelo.component';
import { TarefaRecorrenteEmpresaModeloDetailComponent } from './detail/tarefa-recorrente-empresa-modelo-detail.component';
import { TarefaRecorrenteEmpresaModeloUpdateComponent } from './update/tarefa-recorrente-empresa-modelo-update.component';
import TarefaRecorrenteEmpresaModeloResolve from './route/tarefa-recorrente-empresa-modelo-routing-resolve.service';

const tarefaRecorrenteEmpresaModeloRoute: Routes = [
  {
    path: '',
    component: TarefaRecorrenteEmpresaModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaRecorrenteEmpresaModeloDetailComponent,
    resolve: {
      tarefaRecorrenteEmpresaModelo: TarefaRecorrenteEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaRecorrenteEmpresaModeloUpdateComponent,
    resolve: {
      tarefaRecorrenteEmpresaModelo: TarefaRecorrenteEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaRecorrenteEmpresaModeloUpdateComponent,
    resolve: {
      tarefaRecorrenteEmpresaModelo: TarefaRecorrenteEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaRecorrenteEmpresaModeloRoute;
