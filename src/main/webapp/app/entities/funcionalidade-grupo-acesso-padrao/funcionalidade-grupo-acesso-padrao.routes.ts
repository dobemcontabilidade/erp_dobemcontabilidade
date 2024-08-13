import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FuncionalidadeGrupoAcessoPadraoComponent } from './list/funcionalidade-grupo-acesso-padrao.component';
import { FuncionalidadeGrupoAcessoPadraoDetailComponent } from './detail/funcionalidade-grupo-acesso-padrao-detail.component';
import { FuncionalidadeGrupoAcessoPadraoUpdateComponent } from './update/funcionalidade-grupo-acesso-padrao-update.component';
import FuncionalidadeGrupoAcessoPadraoResolve from './route/funcionalidade-grupo-acesso-padrao-routing-resolve.service';

const funcionalidadeGrupoAcessoPadraoRoute: Routes = [
  {
    path: '',
    component: FuncionalidadeGrupoAcessoPadraoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuncionalidadeGrupoAcessoPadraoDetailComponent,
    resolve: {
      funcionalidadeGrupoAcessoPadrao: FuncionalidadeGrupoAcessoPadraoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuncionalidadeGrupoAcessoPadraoUpdateComponent,
    resolve: {
      funcionalidadeGrupoAcessoPadrao: FuncionalidadeGrupoAcessoPadraoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuncionalidadeGrupoAcessoPadraoUpdateComponent,
    resolve: {
      funcionalidadeGrupoAcessoPadrao: FuncionalidadeGrupoAcessoPadraoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default funcionalidadeGrupoAcessoPadraoRoute;
