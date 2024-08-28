import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TermoContratoAssinaturaEmpresaComponent } from './list/termo-contrato-assinatura-empresa.component';
import { TermoContratoAssinaturaEmpresaDetailComponent } from './detail/termo-contrato-assinatura-empresa-detail.component';
import { TermoContratoAssinaturaEmpresaUpdateComponent } from './update/termo-contrato-assinatura-empresa-update.component';
import TermoContratoAssinaturaEmpresaResolve from './route/termo-contrato-assinatura-empresa-routing-resolve.service';

const termoContratoAssinaturaEmpresaRoute: Routes = [
  {
    path: '',
    component: TermoContratoAssinaturaEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TermoContratoAssinaturaEmpresaDetailComponent,
    resolve: {
      termoContratoAssinaturaEmpresa: TermoContratoAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TermoContratoAssinaturaEmpresaUpdateComponent,
    resolve: {
      termoContratoAssinaturaEmpresa: TermoContratoAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TermoContratoAssinaturaEmpresaUpdateComponent,
    resolve: {
      termoContratoAssinaturaEmpresa: TermoContratoAssinaturaEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default termoContratoAssinaturaEmpresaRoute;
