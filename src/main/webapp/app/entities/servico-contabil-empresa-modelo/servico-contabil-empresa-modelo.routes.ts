import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServicoContabilEmpresaModeloComponent } from './list/servico-contabil-empresa-modelo.component';
import { ServicoContabilEmpresaModeloDetailComponent } from './detail/servico-contabil-empresa-modelo-detail.component';
import { ServicoContabilEmpresaModeloUpdateComponent } from './update/servico-contabil-empresa-modelo-update.component';
import ServicoContabilEmpresaModeloResolve from './route/servico-contabil-empresa-modelo-routing-resolve.service';

const servicoContabilEmpresaModeloRoute: Routes = [
  {
    path: '',
    component: ServicoContabilEmpresaModeloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServicoContabilEmpresaModeloDetailComponent,
    resolve: {
      servicoContabilEmpresaModelo: ServicoContabilEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServicoContabilEmpresaModeloUpdateComponent,
    resolve: {
      servicoContabilEmpresaModelo: ServicoContabilEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServicoContabilEmpresaModeloUpdateComponent,
    resolve: {
      servicoContabilEmpresaModelo: ServicoContabilEmpresaModeloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicoContabilEmpresaModeloRoute;
