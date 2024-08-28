import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CertificadoDigitalEmpresaComponent } from './list/certificado-digital-empresa.component';
import { CertificadoDigitalEmpresaDetailComponent } from './detail/certificado-digital-empresa-detail.component';
import { CertificadoDigitalEmpresaUpdateComponent } from './update/certificado-digital-empresa-update.component';
import CertificadoDigitalEmpresaResolve from './route/certificado-digital-empresa-routing-resolve.service';

const certificadoDigitalEmpresaRoute: Routes = [
  {
    path: '',
    component: CertificadoDigitalEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CertificadoDigitalEmpresaDetailComponent,
    resolve: {
      certificadoDigitalEmpresa: CertificadoDigitalEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CertificadoDigitalEmpresaUpdateComponent,
    resolve: {
      certificadoDigitalEmpresa: CertificadoDigitalEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CertificadoDigitalEmpresaUpdateComponent,
    resolve: {
      certificadoDigitalEmpresa: CertificadoDigitalEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default certificadoDigitalEmpresaRoute;
