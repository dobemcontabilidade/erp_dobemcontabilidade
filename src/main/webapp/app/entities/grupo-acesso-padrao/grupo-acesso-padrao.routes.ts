import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GrupoAcessoPadraoComponent } from './list/grupo-acesso-padrao.component';
import { GrupoAcessoPadraoDetailComponent } from './detail/grupo-acesso-padrao-detail.component';
import { GrupoAcessoPadraoUpdateComponent } from './update/grupo-acesso-padrao-update.component';
import GrupoAcessoPadraoResolve from './route/grupo-acesso-padrao-routing-resolve.service';

const grupoAcessoPadraoRoute: Routes = [
  {
    path: '',
    component: GrupoAcessoPadraoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrupoAcessoPadraoDetailComponent,
    resolve: {
      grupoAcessoPadrao: GrupoAcessoPadraoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrupoAcessoPadraoUpdateComponent,
    resolve: {
      grupoAcessoPadrao: GrupoAcessoPadraoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrupoAcessoPadraoUpdateComponent,
    resolve: {
      grupoAcessoPadrao: GrupoAcessoPadraoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default grupoAcessoPadraoRoute;
