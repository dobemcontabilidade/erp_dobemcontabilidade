import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DocumentoTarefaComponent } from './list/documento-tarefa.component';
import { DocumentoTarefaDetailComponent } from './detail/documento-tarefa-detail.component';
import { DocumentoTarefaUpdateComponent } from './update/documento-tarefa-update.component';
import DocumentoTarefaResolve from './route/documento-tarefa-routing-resolve.service';

const documentoTarefaRoute: Routes = [
  {
    path: '',
    component: DocumentoTarefaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentoTarefaDetailComponent,
    resolve: {
      documentoTarefa: DocumentoTarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentoTarefaUpdateComponent,
    resolve: {
      documentoTarefa: DocumentoTarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentoTarefaUpdateComponent,
    resolve: {
      documentoTarefa: DocumentoTarefaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default documentoTarefaRoute;
