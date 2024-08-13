import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaEmpresaModeloComponent } from './list/tarefa-empresa-modelo.component';
import { TarefaEmpresaModeloDetailComponent } from './detail/tarefa-empresa-modelo-detail.component';
import { TarefaEmpresaModeloUpdateComponent } from './update/tarefa-empresa-modelo-update.component';
import TarefaEmpresaModeloResolve from './route/tarefa-empresa-modelo-routing-resolve.service';

const tarefaEmpresaModeloRoute: Routes = [
  {
    path: '',
    component: TarefaEmpresaModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaEmpresaModeloDetailComponent,
    resolve: {
      tarefaEmpresaModelo: TarefaEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaEmpresaModeloUpdateComponent,
    resolve: {
      tarefaEmpresaModelo: TarefaEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaEmpresaModeloUpdateComponent,
    resolve: {
      tarefaEmpresaModelo: TarefaEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaEmpresaModeloRoute;
