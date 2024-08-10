import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TarefaEmpresaComponent } from './list/tarefa-empresa.component';
import { TarefaEmpresaDetailComponent } from './detail/tarefa-empresa-detail.component';
import { TarefaEmpresaUpdateComponent } from './update/tarefa-empresa-update.component';
import TarefaEmpresaResolve from './route/tarefa-empresa-routing-resolve.service';

const tarefaEmpresaRoute: Routes = [
  {
    path: '',
    component: TarefaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarefaEmpresaDetailComponent,
    resolve: {
      tarefaEmpresa: TarefaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarefaEmpresaUpdateComponent,
    resolve: {
      tarefaEmpresa: TarefaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarefaEmpresaUpdateComponent,
    resolve: {
      tarefaEmpresa: TarefaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tarefaEmpresaRoute;
