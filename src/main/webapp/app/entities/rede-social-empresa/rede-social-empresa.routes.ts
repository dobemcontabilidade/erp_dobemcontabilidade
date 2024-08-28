import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RedeSocialEmpresaComponent } from './list/rede-social-empresa.component';
import { RedeSocialEmpresaDetailComponent } from './detail/rede-social-empresa-detail.component';
import { RedeSocialEmpresaUpdateComponent } from './update/rede-social-empresa-update.component';
import RedeSocialEmpresaResolve from './route/rede-social-empresa-routing-resolve.service';

const redeSocialEmpresaRoute: Routes = [
  {
    path: '',
    component: RedeSocialEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RedeSocialEmpresaDetailComponent,
    resolve: {
      redeSocialEmpresa: RedeSocialEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RedeSocialEmpresaUpdateComponent,
    resolve: {
      redeSocialEmpresa: RedeSocialEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RedeSocialEmpresaUpdateComponent,
    resolve: {
      redeSocialEmpresa: RedeSocialEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default redeSocialEmpresaRoute;
