import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EnderecoEmpresaComponent } from './list/endereco-empresa.component';
import { EnderecoEmpresaDetailComponent } from './detail/endereco-empresa-detail.component';
import { EnderecoEmpresaUpdateComponent } from './update/endereco-empresa-update.component';
import EnderecoEmpresaResolve from './route/endereco-empresa-routing-resolve.service';

const enderecoEmpresaRoute: Routes = [
  {
    path: '',
    component: EnderecoEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnderecoEmpresaDetailComponent,
    resolve: {
      enderecoEmpresa: EnderecoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnderecoEmpresaUpdateComponent,
    resolve: {
      enderecoEmpresa: EnderecoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnderecoEmpresaUpdateComponent,
    resolve: {
      enderecoEmpresa: EnderecoEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default enderecoEmpresaRoute;
