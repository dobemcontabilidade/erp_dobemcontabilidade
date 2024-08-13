import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GrupoAcessoEmpresaComponent } from './list/grupo-acesso-empresa.component';
import { GrupoAcessoEmpresaDetailComponent } from './detail/grupo-acesso-empresa-detail.component';
import { GrupoAcessoEmpresaUpdateComponent } from './update/grupo-acesso-empresa-update.component';
import GrupoAcessoEmpresaResolve from './route/grupo-acesso-empresa-routing-resolve.service';

const grupoAcessoEmpresaRoute: Routes = [
  {
    path: '',
    component: GrupoAcessoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrupoAcessoEmpresaDetailComponent,
    resolve: {
      grupoAcessoEmpresa: GrupoAcessoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrupoAcessoEmpresaUpdateComponent,
    resolve: {
      grupoAcessoEmpresa: GrupoAcessoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrupoAcessoEmpresaUpdateComponent,
    resolve: {
      grupoAcessoEmpresa: GrupoAcessoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default grupoAcessoEmpresaRoute;
