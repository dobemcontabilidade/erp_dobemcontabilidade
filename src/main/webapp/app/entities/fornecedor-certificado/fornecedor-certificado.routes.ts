import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FornecedorCertificadoComponent } from './list/fornecedor-certificado.component';
import { FornecedorCertificadoDetailComponent } from './detail/fornecedor-certificado-detail.component';
import { FornecedorCertificadoUpdateComponent } from './update/fornecedor-certificado-update.component';
import FornecedorCertificadoResolve from './route/fornecedor-certificado-routing-resolve.service';

const fornecedorCertificadoRoute: Routes = [
  {
    path: '',
    component: FornecedorCertificadoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FornecedorCertificadoDetailComponent,
    resolve: {
      fornecedorCertificado: FornecedorCertificadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FornecedorCertificadoUpdateComponent,
    resolve: {
      fornecedorCertificado: FornecedorCertificadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FornecedorCertificadoUpdateComponent,
    resolve: {
      fornecedorCertificado: FornecedorCertificadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fornecedorCertificadoRoute;
