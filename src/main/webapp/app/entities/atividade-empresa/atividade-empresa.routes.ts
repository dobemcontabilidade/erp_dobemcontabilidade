import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AtividadeEmpresaComponent } from './list/atividade-empresa.component';
import { AtividadeEmpresaDetailComponent } from './detail/atividade-empresa-detail.component';
import { AtividadeEmpresaUpdateComponent } from './update/atividade-empresa-update.component';
import AtividadeEmpresaResolve from './route/atividade-empresa-routing-resolve.service';

const atividadeEmpresaRoute: Routes = [
  {
    path: '',
    component: AtividadeEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AtividadeEmpresaDetailComponent,
    resolve: {
      atividadeEmpresa: AtividadeEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AtividadeEmpresaUpdateComponent,
    resolve: {
      atividadeEmpresa: AtividadeEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AtividadeEmpresaUpdateComponent,
    resolve: {
      atividadeEmpresa: AtividadeEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default atividadeEmpresaRoute;
