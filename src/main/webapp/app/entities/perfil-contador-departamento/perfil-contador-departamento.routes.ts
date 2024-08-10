import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PerfilContadorDepartamentoComponent } from './list/perfil-contador-departamento.component';
import { PerfilContadorDepartamentoDetailComponent } from './detail/perfil-contador-departamento-detail.component';
import { PerfilContadorDepartamentoUpdateComponent } from './update/perfil-contador-departamento-update.component';
import PerfilContadorDepartamentoResolve from './route/perfil-contador-departamento-routing-resolve.service';

const perfilContadorDepartamentoRoute: Routes = [
  {
    path: '',
    component: PerfilContadorDepartamentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfilContadorDepartamentoDetailComponent,
    resolve: {
      perfilContadorDepartamento: PerfilContadorDepartamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfilContadorDepartamentoUpdateComponent,
    resolve: {
      perfilContadorDepartamento: PerfilContadorDepartamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfilContadorDepartamentoUpdateComponent,
    resolve: {
      perfilContadorDepartamento: PerfilContadorDepartamentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default perfilContadorDepartamentoRoute;
