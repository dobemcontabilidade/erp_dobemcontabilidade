import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UsuarioErpComponent } from './list/usuario-erp.component';
import { UsuarioErpDetailComponent } from './detail/usuario-erp-detail.component';
import { UsuarioErpUpdateComponent } from './update/usuario-erp-update.component';
import UsuarioErpResolve from './route/usuario-erp-routing-resolve.service';

const usuarioErpRoute: Routes = [
  {
    path: '',
    component: UsuarioErpComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsuarioErpDetailComponent,
    resolve: {
      usuarioErp: UsuarioErpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsuarioErpUpdateComponent,
    resolve: {
      usuarioErp: UsuarioErpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsuarioErpUpdateComponent,
    resolve: {
      usuarioErp: UsuarioErpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default usuarioErpRoute;
