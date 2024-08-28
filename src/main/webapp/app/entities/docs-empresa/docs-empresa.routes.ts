import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DocsEmpresaComponent } from './list/docs-empresa.component';
import { DocsEmpresaDetailComponent } from './detail/docs-empresa-detail.component';
import { DocsEmpresaUpdateComponent } from './update/docs-empresa-update.component';
import DocsEmpresaResolve from './route/docs-empresa-routing-resolve.service';

const docsEmpresaRoute: Routes = [
  {
    path: '',
    component: DocsEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocsEmpresaDetailComponent,
    resolve: {
      docsEmpresa: DocsEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocsEmpresaUpdateComponent,
    resolve: {
      docsEmpresa: DocsEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocsEmpresaUpdateComponent,
    resolve: {
      docsEmpresa: DocsEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default docsEmpresaRoute;
