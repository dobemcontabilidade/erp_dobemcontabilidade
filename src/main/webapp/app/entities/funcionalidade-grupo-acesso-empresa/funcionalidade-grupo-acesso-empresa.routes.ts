import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FuncionalidadeGrupoAcessoEmpresaComponent } from './list/funcionalidade-grupo-acesso-empresa.component';
import { FuncionalidadeGrupoAcessoEmpresaDetailComponent } from './detail/funcionalidade-grupo-acesso-empresa-detail.component';
import { FuncionalidadeGrupoAcessoEmpresaUpdateComponent } from './update/funcionalidade-grupo-acesso-empresa-update.component';
import FuncionalidadeGrupoAcessoEmpresaResolve from './route/funcionalidade-grupo-acesso-empresa-routing-resolve.service';

const funcionalidadeGrupoAcessoEmpresaRoute: Routes = [
  {
    path: '',
    component: FuncionalidadeGrupoAcessoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuncionalidadeGrupoAcessoEmpresaDetailComponent,
    resolve: {
      funcionalidadeGrupoAcessoEmpresa: FuncionalidadeGrupoAcessoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuncionalidadeGrupoAcessoEmpresaUpdateComponent,
    resolve: {
      funcionalidadeGrupoAcessoEmpresa: FuncionalidadeGrupoAcessoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuncionalidadeGrupoAcessoEmpresaUpdateComponent,
    resolve: {
      funcionalidadeGrupoAcessoEmpresa: FuncionalidadeGrupoAcessoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default funcionalidadeGrupoAcessoEmpresaRoute;
