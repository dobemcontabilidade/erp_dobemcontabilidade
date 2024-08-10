import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EmpresaComponent } from './list/empresa.component';
import { EmpresaDetailComponent } from './detail/empresa-detail.component';
import { EmpresaUpdateComponent } from './update/empresa-update.component';
import EmpresaResolve from './route/empresa-routing-resolve.service';

const empresaRoute: Routes = [
  {
    path: '',
    component: EmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmpresaDetailComponent,
    resolve: {
      empresa: EmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default empresaRoute;
