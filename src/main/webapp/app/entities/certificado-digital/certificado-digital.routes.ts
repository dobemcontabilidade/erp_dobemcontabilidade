import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CertificadoDigitalComponent } from './list/certificado-digital.component';
import { CertificadoDigitalDetailComponent } from './detail/certificado-digital-detail.component';
import { CertificadoDigitalUpdateComponent } from './update/certificado-digital-update.component';
import CertificadoDigitalResolve from './route/certificado-digital-routing-resolve.service';

const certificadoDigitalRoute: Routes = [
  {
    path: '',
    component: CertificadoDigitalComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CertificadoDigitalDetailComponent,
    resolve: {
      certificadoDigital: CertificadoDigitalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CertificadoDigitalUpdateComponent,
    resolve: {
      certificadoDigital: CertificadoDigitalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CertificadoDigitalUpdateComponent,
    resolve: {
      certificadoDigital: CertificadoDigitalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default certificadoDigitalRoute;
