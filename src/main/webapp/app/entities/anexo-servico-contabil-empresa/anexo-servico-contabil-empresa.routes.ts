import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AnexoServicoContabilEmpresaComponent } from './list/anexo-servico-contabil-empresa.component';
import { AnexoServicoContabilEmpresaDetailComponent } from './detail/anexo-servico-contabil-empresa-detail.component';
import { AnexoServicoContabilEmpresaUpdateComponent } from './update/anexo-servico-contabil-empresa-update.component';
import AnexoServicoContabilEmpresaResolve from './route/anexo-servico-contabil-empresa-routing-resolve.service';

const anexoServicoContabilEmpresaRoute: Routes = [
  {
    path: '',
    component: AnexoServicoContabilEmpresaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoServicoContabilEmpresaDetailComponent,
    resolve: {
      anexoServicoContabilEmpresa: AnexoServicoContabilEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoServicoContabilEmpresaUpdateComponent,
    resolve: {
      anexoServicoContabilEmpresa: AnexoServicoContabilEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoServicoContabilEmpresaUpdateComponent,
    resolve: {
      anexoServicoContabilEmpresa: AnexoServicoContabilEmpresaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default anexoServicoContabilEmpresaRoute;
