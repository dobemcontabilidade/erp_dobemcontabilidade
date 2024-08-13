import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UsuarioEmpresaComponent } from './list/usuario-empresa.component';
import { UsuarioEmpresaDetailComponent } from './detail/usuario-empresa-detail.component';
import { UsuarioEmpresaUpdateComponent } from './update/usuario-empresa-update.component';
import UsuarioEmpresaResolve from './route/usuario-empresa-routing-resolve.service';

const usuarioEmpresaRoute: Routes = [
  {
    path: '',
    component: UsuarioEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsuarioEmpresaDetailComponent,
    resolve: {
      usuarioEmpresa: UsuarioEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsuarioEmpresaUpdateComponent,
    resolve: {
      usuarioEmpresa: UsuarioEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsuarioEmpresaUpdateComponent,
    resolve: {
      usuarioEmpresa: UsuarioEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default usuarioEmpresaRoute;
