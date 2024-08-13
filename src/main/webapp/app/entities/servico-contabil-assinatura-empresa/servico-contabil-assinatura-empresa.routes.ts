import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServicoContabilAssinaturaEmpresaComponent } from './list/servico-contabil-assinatura-empresa.component';
import { ServicoContabilAssinaturaEmpresaDetailComponent } from './detail/servico-contabil-assinatura-empresa-detail.component';
import { ServicoContabilAssinaturaEmpresaUpdateComponent } from './update/servico-contabil-assinatura-empresa-update.component';
import ServicoContabilAssinaturaEmpresaResolve from './route/servico-contabil-assinatura-empresa-routing-resolve.service';

const servicoContabilAssinaturaEmpresaRoute: Routes = [
  {
    path: '',
    component: ServicoContabilAssinaturaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServicoContabilAssinaturaEmpresaDetailComponent,
    resolve: {
      servicoContabilAssinaturaEmpresa: ServicoContabilAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServicoContabilAssinaturaEmpresaUpdateComponent,
    resolve: {
      servicoContabilAssinaturaEmpresa: ServicoContabilAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServicoContabilAssinaturaEmpresaUpdateComponent,
    resolve: {
      servicoContabilAssinaturaEmpresa: ServicoContabilAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicoContabilAssinaturaEmpresaRoute;
